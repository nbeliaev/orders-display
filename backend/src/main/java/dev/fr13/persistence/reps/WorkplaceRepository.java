package dev.fr13.persistence.reps;

import dev.fr13.domain.Workplace;

import java.util.List;
import java.util.Optional;

public interface WorkplaceRepository {

    List<Workplace> findAllActive();

    Optional<Workplace> findByUuid(String uuid);

    Workplace save(Workplace workplace);
}
