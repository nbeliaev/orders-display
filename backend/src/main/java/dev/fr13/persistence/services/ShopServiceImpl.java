package dev.fr13.persistence.services;

import dev.fr13.domain.Client;
import dev.fr13.domain.Shop;
import dev.fr13.dtos.ShopDto;
import dev.fr13.exceptions.NoSuchClientException;
import dev.fr13.persistence.reps.ShopRepository;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

    private final ShopRepository repository;
    private final ClientService clientService;
    private final Convertor<Shop, ShopDto> convertor;

    public ShopServiceImpl(ShopRepository repository,
                           ClientService clientService,
                           @Qualifier("shop") Convertor<Shop, ShopDto> convertor) {
        this.repository = repository;
        this.clientService = clientService;
        this.convertor = convertor;
    }

    @Override
    public ShopDto save(ShopDto dto) {
        log.debug("Save shop {}", dto);
        var shop = convertor.toEntity(dto);
        var client = findClientByUuid(dto.getClient());
        shop.setClient(client);
        repository.findByUuidAndClient(dto.getUuid(), client).
                ifPresent(i -> shop.setId(i.getId()));
        var persisted = repository.save(shop);
        return convertor.toDto(persisted);
    }

    @Override
    public List<ShopDto> findAllByClientId() {
        log.debug("Get shops list");
        return convertor.listEntitiesToListDtos(repository.findAll());
    }

    @Override
    public Optional<Shop> findByUuidAndClient(String uuid, Client client) {
        log.debug("Find shop by uuid {} and client {}", uuid, client);
        return repository.findByUuidAndClient(uuid, client);
    }

    private Client findClientByUuid(String uuid) {
        return clientService.findByUuidAndActive(uuid).orElseThrow(() -> new NoSuchClientException(uuid));
    }
}
