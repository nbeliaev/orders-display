package dev.fr13.persistence.reps;

import dev.fr13.domain.Client;
import dev.fr13.domain.Shop;
import dev.fr13.domain.Workplace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WorkplaceRepository extends MongoRepository<Workplace, String> {

    List<Workplace> findAllByShopAndClientAndActiveTrue(Shop shop, Client client);

    Optional<Workplace> findByUuid(String uuid);

    Optional<Workplace> findByUuidAndShopAndClientAndActiveTrue(String uuid, Shop shop, Client client);
}
