package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WorkplaceServiceInMemory implements WorkplaceService {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceServiceInMemory.class);

    private static final List<Workplace> workplaces = new ArrayList<>();
    private final ModelMapper mapper;

    static {
        var kitchen = new Workplace("K", "Kitchen #1");
        var bar = new Workplace("B", "Bar");
        workplaces.add(kitchen);
        workplaces.add(bar);
    }

    public WorkplaceServiceInMemory(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<WorkplaceDto> findAllActive() {
        log.debug("Find all active workplaces");
        return workplaces.stream()
                .filter(Workplace::isActive)
                .map(i -> mapper.map(i, WorkplaceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Workplace findByUuid(String uuid) {
        return workplaces.stream()
                .filter(i -> i.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Workplace with uuid %s was not found.", uuid)));
    }

    @Override
    public WorkplaceDto save(WorkplaceDto dto) {
        var workplace = mapper.map(dto, Workplace.class);
        log.debug("Save {}", workplace);
        workplaces.add(workplace);
        return dto;
    }
}
