package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;

import java.util.List;

public interface WorkplaceService {

    List<WorkplaceDto> findAllActive();

    Workplace findByUuid(String uuid);

    WorkplaceDto save(WorkplaceDto dto);
}
