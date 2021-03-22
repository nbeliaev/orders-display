package dev.fr13.persistence.reps;

import dev.fr13.domain.Order;
import dev.fr13.domain.OrderItem;
import dev.fr13.domain.Workplace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderRepositoryInMemory implements OrderRepository {
    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryInMemory.class);

    private static final Map<String, Order> orders = new HashMap<>();

    private final Random random = new Random();

    @Value("${app.random.tables}")
    private List<String> tables;
    @Value("${app.random.dishes}")
    private List<String> dishes;

    @Override
    public List<Order> findByWorkplace(Workplace workplace) {
        log.debug("Find all orders by workplace {}", workplace);
        addNewOrder(workplace);
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
        return result;
    }

    @Override
    public List<Order> findAll() {
        log.debug("Find all orders");
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order save(Order order) {
        log.debug("Save {}", order);
        orders.put(order.getUuid(), order);
        return order;
    }

    @Override
    public void deleteByUuid(String uuid) {
        if (orders.containsKey(uuid)) {
            log.debug("Delete by uuid {}", uuid);
            orders.remove(uuid);
        }
    }

    private void addNewOrder(Workplace workplace) {
        var tableNumber = getRandomNumber(0, 4);
        var order = new Order(
                UUID.randomUUID().toString(),
                System.currentTimeMillis(),
                tables.get(tableNumber));

        var itemsNumber = getRandomNumber(1, 7);
        for (int i = 0; i < itemsNumber; i++) {
            var nameNumber = getRandomNumber(0, 4);
            var note = i % 2 == 0 ? "spicy" : "deep fried";
            var item = new OrderItem(i, workplace, "new", dishes.get(nameNumber), getRandomNumber(1, 100), note);
            order.addItem(item);
        }

        orders.put(order.getUuid(), order);
    }

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
