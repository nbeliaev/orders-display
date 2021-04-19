package dev.fr13.util.convertor;

import dev.fr13.domain.Client;
import dev.fr13.dtos.ClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("client")
public class ClientMapper implements Convertor<Client, ClientDto> {
    private final ModelMapper mapper;

    public ClientMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Client toEntity(ClientDto dto) {
        return mapper.map(dto, Client.class);
    }

    @Override
    public ClientDto toDto(Client entity) {
        return mapper.map(entity, ClientDto.class);
    }

    @Override
    public List<ClientDto> listEntitiesToListDtos(List<Client> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> listDtosToListEntities(List<ClientDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
