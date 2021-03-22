package dev.fr13.persistence.reps;

import dev.fr13.domain.Order;
import dev.fr13.domain.Workplace;

import java.util.List;

public interface OrderRepository {

    List<Order> findByWorkplace(Workplace workplace);

    List<Order> findAll();

    Order save(Order order);

    void deleteByUuid(String uuid);
}
