package dev.fr13.persistence.reps;

import dev.fr13.domain.Client;
import dev.fr13.domain.Order;
import dev.fr13.domain.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByClientAndShop(Client client, Shop shop);

    Optional<Order> findByUuidAndClientAndShop(String uuid, Client client, Shop shop);

    Optional<Order> deleteByUuidAndClientAndShop(String uuid, Client client, Shop shop);
}
