package dev.fr13.persistence.reps;

import dev.fr13.domain.Order;
import dev.fr13.domain.Workplace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderRepositoryInMemory implements OrderRepository {
    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryInMemory.class);

    private static final Map<String, Order> orders = new HashMap<>();

    @Override
    public List<Order> findByWorkplace(Workplace workplace) {
        log.debug("Find all orders by workplace {}", workplace);
        var result = new ArrayList<Order>();
        for (Order order : orders.values()) {
            var isThereSuitableItems = order.getItems().stream()
                    .anyMatch(i -> i.getWorkplace().equals(workplace));
            if (isThereSuitableItems) {
                var tempOrder = new Order(order.getUuid(), order.getTimestamp(), order.getTable());
                var items = order.getItems().stream()
                        .filter(i -> i.getWorkplace().equals(workplace))
                        .collect(Collectors.toCollection(ArrayList::new));
                tempOrder.setItems(items);
                result.add(tempOrder);
            }
        }
        return sortByTimestamp(result);
    }

    @Override
    public List<Order> findAll() {
        log.debug("Find all orders");
        return sortByTimestamp(orders.values());
    }

    @Override
    public Order save(Order order) {
        log.debug("Save {}", order);
        orders.put(order.getUuid(), order);
        return order;
    }

    @Override
    public Optional<Order> findByUuid(String uuid) {
        var order = orders.get(uuid);
        if (order == null) {
            return Optional.empty();
        } else {
            return Optional.of(order);
        }
    }

    @Override
    public void deleteByUuid(String uuid) {
        if (orders.containsKey(uuid)) {
            log.debug("Delete by uuid {}", uuid);
            orders.remove(uuid);
        }
    }

    private List<Order> sortByTimestamp(Collection<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparingLong(Order::getTimestamp))
                .collect(Collectors.toList());
    }
}
