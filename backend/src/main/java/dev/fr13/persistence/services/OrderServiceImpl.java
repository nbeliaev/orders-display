package dev.fr13.persistence.services;

import dev.fr13.domain.Order;
import dev.fr13.domain.OrderItemStatus;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderDto;
import dev.fr13.persistence.reps.OrderRepository;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final Convertor<Order, OrderDto> convertor;
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository,
                            @Qualifier("order") Convertor<Order, OrderDto> convertor) {
        this.convertor = convertor;
        this.repository = repository;
    }

    @Override
    public List<OrderDto> findByWorkplace(Workplace workplace) {
        log.debug("Find all orders by workplace {}", workplace);
        var orders = repository.findByWorkplace(workplace);
        return convertor.listEntitiesToListDtos(orders);
    }

    @Override
    public List<OrderDto> findAll() {
        log.debug("Find all orders");
        var orders = repository.findAll();
        return convertor.listEntitiesToListDtos(orders);
    }

    @Override
    public OrderDto saveOrUpdate(OrderDto dto) {
        log.debug("Save {}", dto);
        var order = convertor.toEntity(dto);
        fillStatuses(order);
        repository.save(order);
        return convertor.toDto(order);
    }

    @Override
    public void deleteByUuid(String uuid) {
        log.debug("Delete by uuid {}", uuid);
        repository.deleteByUuid(uuid);
    }

    /*
    There are two cases:
        1. An order was received from 1C side.
            In this case status will be empty always.
            If it's a new order we should set the status NEW for all items
            If it's a persisted order we should set the status according persisted statuses
        2. An order was received from Frontend side.
            In this case status manages on Fronted side.
            We can't receive a new order from Frontend side.
            We should do nothing.
     */
    private void fillStatuses(Order order) {
        var optnOrder = repository.findByUuid(order.getUuid());
        if (optnOrder.isEmpty()) {
            order.getItems().forEach(i -> i.setStatus(OrderItemStatus.NEW));
        } else {
            var persistedOrder = optnOrder.get();
            var items = order.getItems();
            var persistedItems = persistedOrder.getItems();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getStatus() == OrderItemStatus.EMPTY) {
                    var status = persistedItems.get(i).getStatus();
                    items.get(i).setStatus(status);
                }
            }
        }
    }
}
