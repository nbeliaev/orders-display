package dev.fr13.persistence.services;

import dev.fr13.domain.Order;
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
        repository.save(order);
        return dto;
    }

    @Override
    public void deleteByUuid(String uuid) {
        log.debug("Delete by uuid {}", uuid);
        repository.deleteByUuid(uuid);
    }
}
