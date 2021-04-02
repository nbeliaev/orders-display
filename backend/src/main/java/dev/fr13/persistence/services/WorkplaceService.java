package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;

import java.util.List;
import java.util.Optional;

public interface WorkplaceService {

    List<WorkplaceDto> findAllActiveByShopIdAndClientId(String clientId, String shopId);

    Optional<Workplace> findByUuid(String uuid);

    WorkplaceDto save(WorkplaceDto dto);
}
