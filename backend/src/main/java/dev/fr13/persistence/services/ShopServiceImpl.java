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
        var client = clientService.findByUuidAndActive(dto.getClient())
                .orElseThrow(() -> new NoSuchClientException(dto.getClient()));
        var shop = convertor.toEntity(dto);
        shop.setClient(client);
        repository.findByUuidAndClientAndActiveTrue(dto.getUuid(), client).
                ifPresent(i -> shop.setId(i.getId()));
        log.debug("Save shop {}", dto);
        var persisted = repository.save(shop);
        return convertor.toDto(persisted);
    }

    @Override
    public List<ShopDto> findAllByClientUuid(String clientUuid) {
        var client = clientService.findByUuidAndActive(clientUuid)
                .orElseThrow(() -> new NoSuchClientException(clientUuid));
        log.debug("Get shops list by client {}", client);
        return convertor.listEntitiesToListDtos(repository.findAllByClientAndActiveTrue(client));
    }

    @Override
    public Optional<Shop> findByUuidAndClientAndActive(String uuid, Client client) {
        log.debug("Find shop by uuid {} and client {}", uuid, client);
        return repository.findByUuidAndClientAndActiveTrue(uuid, client);
    }
}
