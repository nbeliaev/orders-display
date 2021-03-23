package dev.fr13.util.convertor;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("workplace")
public class WorkplaceConvertor implements Convertor<Workplace, WorkplaceDto> {
    private final ModelMapper mapper;

    public WorkplaceConvertor(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Workplace toEntity(WorkplaceDto dto) {
        return mapper.map(dto, Workplace.class);
    }

    @Override
    public WorkplaceDto toDto(Workplace entity) {
        return mapper.map(entity, WorkplaceDto.class);
    }

    @Override
    public List<WorkplaceDto> listEntitiesToListDtos(List<Workplace> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Workplace> listDtosToListEntities(List<WorkplaceDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
