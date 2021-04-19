package dev.fr13.persistence.reps;

import dev.fr13.domain.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {

    Optional<Client> findByUuidAndActiveTrue(String uuid);

    Optional<Client> findByUuid(String uuid);
}
