package dev.fr13.persistence.services;

import dev.fr13.domain.Client;
import dev.fr13.domain.Shop;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;
import dev.fr13.exceptions.NoSuchClientException;
import dev.fr13.exceptions.NoSuchShopException;
import dev.fr13.persistence.reps.WorkplaceRepository;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceServiceImpl.class);

    private final WorkplaceRepository repository;
    private final ClientService clientService;
    private final ShopService shopService;
    private final Convertor<Workplace, WorkplaceDto> convertor;

    public WorkplaceServiceImpl(WorkplaceRepository repository,
                                ClientService clientService,
                                ShopService shopService,
                                @Qualifier("workplace") Convertor<Workplace, WorkplaceDto> convertor) {
        this.repository = repository;
        this.clientService = clientService;
        this.shopService = shopService;
        this.convertor = convertor;
    }

    @Override
    public List<WorkplaceDto> findAllActiveByShopIdAndClientId(String shopId, String clientId) {
        log.debug("Find all active workplaces by client id {} and shop id {}", clientId, shopId);
        var client = clientService.findByUuidAndActive(clientId).orElseThrow(
                () -> new NoSuchClientException(clientId));
        var shop = shopService.findByUuidAndClientAndActive(shopId, client)
                .orElseThrow(() -> new NoSuchShopException(shopId));

        var workplaces = repository.findAllByShopAndClientAndActiveTrue(shop, client);
        return convertor.listEntitiesToListDtos(workplaces);
    }

    @Override
    public Optional<Workplace> findByUuidAndShopAndClient(String uuid, Shop shop, Client client) {
        log.debug("Find workplace by uuid {}, shop {} and client {}", uuid, shop, client);
        return repository.findByUuidAndShopAndClientAndActiveTrue(uuid, shop, client);
    }

    @Override
    public WorkplaceDto save(WorkplaceDto dto) {
        var client = clientService.findByUuidAndActive(dto.getClient())
                .orElseThrow(() -> new NoSuchClientException(dto.getClient()));
        var shop = shopService.findByUuidAndClientAndActive(dto.getShop(), client)
                .orElseThrow(() -> new NoSuchShopException(dto.getShop()));

        var workplace = convertor.toEntity(dto);
        workplace.setClient(client);
        workplace.setShop(shop);
        repository.findByUuidAndShopAndClientAndActiveTrue(workplace.getUuid(), shop, client)
                .ifPresent(i -> workplace.setId(i.getId()));
        log.debug("Save workplace {}", workplace);
        var persisted = repository.save(workplace);
        return convertor.toDto(persisted);
    }
}
