package dev.fr13.persistence.services;

import dev.fr13.domain.Client;
import dev.fr13.domain.Shop;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;

import java.util.List;
import java.util.Optional;

public interface WorkplaceService {

    List<WorkplaceDto> findAllActiveByShopIdAndClientId(String clientId, String shopId);

    Optional<Workplace> findByUuidAndShopAndClient(String uuid, Shop shop, Client client);

    WorkplaceDto save(WorkplaceDto dto);
}
