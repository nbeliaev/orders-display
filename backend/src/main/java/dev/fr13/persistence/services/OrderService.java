package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> findByWorkplace(Workplace workplace);

    List<OrderDto> findAll();

    OrderDto saveOrUpdate(OrderDto dto);

    Optional<OrderDto> deleteByUuid(String uuid);
}
