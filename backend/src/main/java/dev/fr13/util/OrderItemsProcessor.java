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
            log.debug("No empty statuses");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            item.setRowNumber(i);
            item.setStatus(OrderItemStatus.NEW);
        }
    }

    public void refillStatusesAndRowsNumbers(Order source, Order destn) {
        var items = destn.getItems();
        if (noEmptyStatuses(items)) {
            log.debug("No empty statuses");
            return;
        }

        var persistedItems = source.getItems();
        var newItems = newItemsByPersistedItems(items, persistedItems);
        destn.setItems(newItems);

    }

    private List<OrderItem> newItemsByPersistedItems(List<OrderItem> items, List<OrderItem> persistedItems) {
        var newItems = new ArrayList<OrderItem>();
        var rowsCounter = persistedItems.size();
        for (OrderItem item : items) {
            var filteredItems = getFilteredItems(persistedItems, item.getName());
            var tempItems = new ArrayList<>(filteredItems);
            var totalQnt = item.getQnt();
            var totalPersistedQnt = filteredItems.stream().mapToInt(OrderItem::getQnt).sum();
            var qntDiff = totalQnt - totalPersistedQnt;
            if (isNewRow(totalPersistedQnt)) {
                log.debug("Found a new order item. Row number is {}, name is {}", rowsCounter, item.getName());
                item.setRowNumber(rowsCounter++);
                item.setStatus(OrderItemStatus.NEW);
                tempItems.add(item);
            } else if (qntWasIncrease(qntDiff)) {
                log.debug("Quantity has been increased on {}. Add a new order item, row number is {}, name is {}",
                        qntDiff, rowsCounter, item.getName());
                item.setRowNumber(rowsCounter++);
                item.setQnt(qntDiff);
                item.setStatus(OrderItemStatus.NEW);
                tempItems.add(item);
            } else if (qntWasReduce(qntDiff)) {
                log.debug("Quantity has been reduced by {}. Name is {}",
                        qntDiff, item.getName());
                var newItemWasDeleted = false;
                var optnItem = tempItems.stream()
                        .filter(i -> i.getStatus() == OrderItemStatus.NEW)
                        .findFirst();
                if (optnItem.isPresent()) {
                    newItemWasDeleted = true;
                    var i = optnItem.get();
                    if (i.getQnt() > -qntDiff) {
                        reduceQnt(i, qntDiff);
                    } else {
                        tempItems.remove(i);
                        rowsCounter--;
                    }
                }
                if (!newItemWasDeleted) {
                    tempItems.stream()
                            .findFirst()
                            .ifPresent(i -> reduceQntAndAddNoteAboutQntReducing(i, qntDiff));
                }
            }
            newItems.addAll(tempItems);
        }
        return newItems.stream()
                .sorted(Comparator.comparingInt(OrderItem::getRowNumber))
                .collect(Collectors.toList());
    }

    private boolean isNewRow(int totalPersistedQnt) {
        return totalPersistedQnt == 0;
    }

    private boolean qntWasReduce(int qntDiff) {
        return qntDiff < 0;
    }

    private boolean qntWasIncrease(int qntDiff) {
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

    private void reduceQntAndAddNoteAboutQntReducing(OrderItem item, int qnt) {
        var prvNote = item.getNote();
        var reduceMsg = String.format("qnt has been reduced on %s", -qnt);
        if (prvNote.isEmpty()) {
            item.setNote(reduceMsg);
        } else {
            item.setNote(prvNote + "\\n " + reduceMsg);
        }
    }

    private void reduceQnt(OrderItem item, int qnt) {
        item.setQnt(item.getQnt() + qnt);
    }
}