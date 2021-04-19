package dev.fr13.persistence.reps;

import dev.fr13.domain.Client;
import dev.fr13.domain.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends MongoRepository<Shop, String> {

    Optional<Shop> findByUuidAndClientAndActiveTrue(String uuid, Client client);

    List<Shop> findAllByClientAndActiveTrue(Client client);
}
