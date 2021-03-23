package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> findByWorkplace(Workplace workplace);

    List<OrderDto> findAll();

    OrderDto saveOrUpdate(OrderDto dto);

    void deleteByUuid(String uuid);
}
