package dev.fr13.persistence.services;

import dev.fr13.domain.Client;
import dev.fr13.dtos.ClientDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDto save(ClientDto dto);

    List<ClientDto> findAll();

    Optional<Client> findByUuid(String uuid);
}
