package dev.fr13.persistence.services;

import dev.fr13.domain.Client;
import dev.fr13.dtos.ClientDto;
import dev.fr13.persistence.reps.ClientRepository;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceMongoImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceMongoImpl.class);

    private final ClientRepository repository;
    private final Convertor<Client, ClientDto> convertor;

    public ClientServiceMongoImpl(ClientRepository repository,
                                  @Qualifier("client") Convertor<Client, ClientDto> convertor) {
        this.repository = repository;
        this.convertor = convertor;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        log.debug("Save client {}", dto);
        var client = convertor.toEntity(dto);
        repository.findByUuid(dto.getUuid())
                .ifPresent(i -> client.setId(i.getId()));
        var persisted = repository.save(client);
        return convertor.toDto(persisted);
    }

    @Override
    public List<ClientDto> findAll() {
        log.debug("Get clients list");
        return convertor.listEntitiesToListDtos(repository.findAll());
    }

    @Override
    public Optional<Client> findByUuid(String uuid) {
        log.debug("Find client by uuid {}", uuid);
        return repository.findByUuid(uuid);
    }
}
