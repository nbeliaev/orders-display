package dev.fr13.persistence.reps;

import dev.fr13.domain.Workplace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkplaceRepositoryInMemory implements WorkplaceRepository {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceRepositoryInMemory.class);

    private static final Map<String, Workplace> workplaces = new HashMap<>();

    @Override
    public List<Workplace> findAllActive() {
        log.debug("Find all active workplaces");
        return workplaces.values().stream()
                .filter(Workplace::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Workplace> findByUuid(String uuid) {
        log.debug("Find workplace by uuid {}", uuid);
        var workplace = workplaces.get(uuid);
        if (workplace == null) {
            return Optional.empty();
        } else {
            return Optional.of(workplace);
        }
    }

    @Override
    public Workplace save(Workplace workplace) {
        log.debug("Save workplace {}", workplace);
        workplaces.put(workplace.getUuid(), workplace);
        return workplace;
    }
}
