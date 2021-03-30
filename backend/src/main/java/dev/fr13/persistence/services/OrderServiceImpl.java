package dev.fr13.persistence.services;

import dev.fr13.domain.Order;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderDto;
import dev.fr13.persistence.reps.OrderRepository;
import dev.fr13.util.OrderItemsProcessor;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final Convertor<Order, OrderDto> convertor;
    private final OrderRepository repository;
    private final OrderItemsProcessor itemsProcessor;

    public OrderServiceImpl(OrderRepository repository,
                            OrderItemsProcessor itemsProcessor,
                            @Qualifier("order") Convertor<Order, OrderDto> convertor) {
        this.repository = repository;
        this.itemsProcessor = itemsProcessor;
        this.convertor = convertor;
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
        var optnOrder = repository.findByUuid(order.getUuid());
        if (optnOrder.isPresent()) {
            itemsProcessor.refillStatusesAndRowsNumbers(optnOrder.get(), order);
        } else {
            itemsProcessor.setStatusesAndRowsNumbersInNewOrder(order);
        }
        repository.save(order);
        return convertor.toDto(order);
    }

    @Override
    public Optional<OrderDto> deleteByUuid(String uuid) {
        log.debug("Delete by uuid {}", uuid);
        var optnOrder = repository.deleteByUuid(uuid);
        return optnOrder.map(convertor::toDto);
    }
}
