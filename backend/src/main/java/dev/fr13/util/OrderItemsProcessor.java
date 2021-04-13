package dev.fr13.util;

import dev.fr13.domain.Order;
import dev.fr13.domain.OrderItem;
import dev.fr13.domain.OrderItemStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemsProcessor {
    private static final Logger log = LoggerFactory.getLogger(OrderItemsProcessor.class);

    public void setStatusesAndRowsNumbersInNewOrder(Order order) {
        log.debug("Processing of statuses and rows numbers for a new order");
        var items = order.getItems();
        if (noEmptyStatuses(items)) {
            log.debug("Found no empty statuses");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            setRowNumberAndStatusAsNew(item, i);
        }
    }

    public void refillStatusesAndRowsNumbers(Order source, Order destn) {
        var items = destn.getItems();
        var persistedItems = source.getItems();
        if (noEmptyStatuses(items)) {
            log.debug("Found no empty statuses");
            var newItems = addPersistedItems(persistedItems, items);
            destn.setItems(newItems);
        } else {
            var newItems = newItemsByPersistedItems(persistedItems, items);
            destn.setItems(newItems);
        }
    }

    private List<OrderItem> addPersistedItems(List<OrderItem> persistedItems, List<OrderItem> items) {
        var result = new ArrayList<>(items);
        var currentWorkplace = items.stream()
                .map(OrderItem::getWorkplace)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Found no workplace."));

        var itemsByOthersWorkplaces = persistedItems.stream()
                .filter(i -> !i.getWorkplace().equals(currentWorkplace))
                .collect(Collectors.toList());

        result.addAll(itemsByOthersWorkplaces);
        return getSortedItems(result);
    }

    private List<OrderItem> newItemsByPersistedItems(List<OrderItem> persistedItems, List<OrderItem> items) {
        var newItems = new ArrayList<OrderItem>();
        var rowsCounter = persistedItems.size();
        for (OrderItem item : items) {
            var filteredItems = getFilteredItems(persistedItems, item.getName());
            var tempItems = new ArrayList<>(filteredItems);
            var totalPersistedQnt = filteredItems.stream().mapToInt(OrderItem::getQnt).sum();
            var qntDiff = item.getQnt() - totalPersistedQnt;
            if (isNewRow(totalPersistedQnt)) {
                log.debug("Found a new order item. Row number is {}, name is {}", rowsCounter, item.getName());
                setRowNumberAndStatusAsNew(item, rowsCounter);
                tempItems.add(item);
                rowsCounter++;
            } else if (qntWasIncreased(qntDiff)) {
                log.debug("Quantity has been increased on {}. Add a new order item, row number is {}, name is {}",
                        qntDiff, rowsCounter, item.getName());
                setRowNumberAndStatusAsNew(item, rowsCounter);
                item.setQnt(qntDiff);
                tempItems.add(item);
                rowsCounter++;
            } else if (qntWasReduced(qntDiff)) {
                log.debug("Quantity has been reduced by {}. Name is {}",
                        qntDiff, item.getName());
                var itemWasDeleted = false;
                var optnItem = tempItems.stream()
                        .filter(this::isNewStatus)
                        .findFirst();
                if (optnItem.isPresent()) {
                    itemWasDeleted = true;
                    var i = optnItem.get();
                    var itemQntIsBiggerThenQntDiff = i.getQnt() > -qntDiff;
                    if (itemQntIsBiggerThenQntDiff) {
                        reduceQnt(i, -qntDiff);
                    } else {
                        tempItems.remove(i);
                        rowsCounter--;
                    }
                }
                if (!itemWasDeleted) {
                    reduceQntAndAddNote(tempItems, -qntDiff);
                }
            }
            newItems.addAll(tempItems);
        }
        return getSortedItems(newItems);
    }

    private void reduceQntAndAddNote(List<OrderItem> items, int qnt) {
        items.stream()
                .findFirst()
                .ifPresent(i -> {
                    reduceQnt(i, qnt);
                    addNote(i, qnt);
                });
    }

    private void setRowNumberAndStatusAsNew(OrderItem item, int i) {
        item.setRowNumber(i);
        item.setStatus(OrderItemStatus.NEW);
    }

    private List<OrderItem> getSortedItems(ArrayList<OrderItem> newItems) {
        return newItems.stream()
                .sorted(Comparator.comparingInt(OrderItem::getRowNumber))
                .collect(Collectors.toList());
    }

    private boolean isNewRow(int totalPersistedQnt) {
        return totalPersistedQnt == 0;
    }

    private boolean qntWasReduced(int qntDiff) {
        return qntDiff < 0;
    }

    private boolean qntWasIncreased(int qntDiff) {
        return qntDiff > 0;
    }

    private List<OrderItem> getFilteredItems(List<OrderItem> items, String filter) {
        return items.stream()
                .filter(i -> i.getName().equals(filter))
                .collect(Collectors.toList());
    }

    private boolean noEmptyStatuses(List<OrderItem> items) {
        return items.stream().noneMatch(this::isEmptyStatus);
    }

    private boolean isEmptyStatus(OrderItem item) {
        return item.getStatus() == OrderItemStatus.EMPTY;
    }

    private boolean isNewStatus(OrderItem item) {
        return item.getStatus() == OrderItemStatus.NEW;
    }

    private void addNote(OrderItem item, int qnt) {
        var prvNote = item.getNote();
        var reduceMsg = String.format("qnt has been reduced on %s", qnt);
        if (prvNote.isEmpty()) {
            item.setNote(reduceMsg);
        } else {
            item.setNote(prvNote + "\\n " + reduceMsg);
        }
    }

    private void reduceQnt(OrderItem item, int qnt) {
        item.setQnt(item.getQnt() - qnt);
    }
}