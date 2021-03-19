package dev.fr13.persistence.services;

import dev.fr13.domain.Order;
import dev.fr13.domain.OrderItem;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderDto;
import dev.fr13.dtos.OrderItemDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceInMemory implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceInMemory.class);

    private static final Map<String, Order> orders = new HashMap<>();

    @Value("${app.random.tables}")
    private List<String> tables;
    @Value("${app.random.dishes}")
    private List<String> dishes;

    private final ModelMapper mapper;
    private final Random random = new Random();
    private final WorkplaceService workplaceService;

    public OrderServiceInMemory(ModelMapper mapper, WorkplaceService workplaceService) {
        this.mapper = mapper;
        this.workplaceService = workplaceService;
    }

    @Override
    public List<OrderDto> findAllByWorkplace(Workplace workplace) {
        log.debug("Find all orders by workplace {}", workplace);
        addNewOrder(workplace);
        var result = new ArrayList<OrderDto>();
        for (Order order : orders.values()) {
            var isThereSuitableItems = order.getItems().stream()
                    .anyMatch(i -> i.getWorkplace().equals(workplace));
            if (isThereSuitableItems) {
                var orderDto = mapper.map(order, OrderDto.class);
                var items = order.getItems().stream()
                        .filter(i -> i.getWorkplace().equals(workplace))
                        .collect(Collectors.toList())
                        .stream()
                        .map(i -> mapper.map(i, OrderItemDto.class))
                        .collect(Collectors.toList());
                orderDto.setItems(items);
                result.add(orderDto);
            }
        }
        return result;
    }

    @Override
    public List<OrderDto> findAll() {
        log.debug("Find all orders");
        return orders.entrySet().stream()
                .map(entry -> {
                    var orderDto = mapper.map(entry, OrderDto.class);
                    var itemDtos = entry.getValue().getItems().stream()
                            .map(item -> mapper.map(item, OrderItemDto.class))
                            .collect(Collectors.toList());
                    orderDto.setItems(itemDtos);
                    return orderDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto saveOrUpdate(OrderDto dto) {
        log.debug("Save {}", dto);
        var order = mapper.map(dto, Order.class);
        mapWorkplaces(dto, order);
        orders.put(order.getUuid(), order);
        return dto;
    }

    @Override
    public void deleteByUuid(String uuid) {
        if (orders.containsKey(uuid)) {
            log.debug("Delete by uuid {}", uuid);
            orders.remove(uuid);
        }
    }

    private void mapWorkplaces(OrderDto src, Order dstn) {
        var items = dstn.getItems();
        var itemsDto = src.getItems();
        for (int i = 0; i < items.size(); i++) {
            var workplace = workplaceService.findByUuid(
                    itemsDto.get(i).getWorkplace());
            items.get(i).setWorkplace(workplace);
        }
    }

    private void addNewOrder(Workplace workplace) {
        var tableNumber = getRandomNumber(0, 4);
        var order = new Order(
                UUID.randomUUID().toString(),
                System.currentTimeMillis(),
                tables.get(tableNumber));
        if (workplace.getUuid().equals("B")) {
            var item = new OrderItem(0, workplace, "new", "Pan-roasted pastry rolls", 200);
            order.addItem(item);
        } else {
            var itemsNumber = getRandomNumber(1, 7);
            for (int i = 0; i < itemsNumber; i++) {
                var nameNumber = getRandomNumber(0, 4);
                var item = new OrderItem(i, workplace, "new", dishes.get(nameNumber), getRandomNumber(1, 100));
                order.addItem(item);
            }
        }
        orders.put(order.getUuid(), order);
    }

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
