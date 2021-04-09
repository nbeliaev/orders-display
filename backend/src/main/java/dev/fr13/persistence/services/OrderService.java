package dev.fr13.persistence.services;

import dev.fr13.dtos.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> findByClientAndShopAndWorkplace(String clientUuid, String shopUuid, String workplaceUuid);

    OrderDto save(OrderDto dto);

    Optional<OrderDto> deleteByClientAndShopAndUuid(String clientUuid, String shopUuid, String orderUuid);
}
