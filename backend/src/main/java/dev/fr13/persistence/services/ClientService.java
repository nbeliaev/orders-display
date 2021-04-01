package dev.fr13.persistence.services;

import dev.fr13.dtos.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto dto);

    List<ClientDto> findAll();
}
