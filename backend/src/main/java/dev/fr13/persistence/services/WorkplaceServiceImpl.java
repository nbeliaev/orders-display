package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;
import dev.fr13.persistence.reps.WorkplaceRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceServiceImpl.class);

    private final ModelMapper mapper;
    private final WorkplaceRepository repository;

    public WorkplaceServiceImpl(ModelMapper mapper, WorkplaceRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<WorkplaceDto> findAllActive() {
        log.debug("Find all active workplaces");
        var workplaces = repository.findAllActive();
        return workplaces.stream()
                .map(i -> mapper.map(i, WorkplaceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Workplace> findByUuid(String uuid) {
        return repository.findByUuid(uuid);
    }

    @Override
    public WorkplaceDto save(WorkplaceDto dto) {
        var workplace = mapper.map(dto, Workplace.class);
        log.debug("Save {}", workplace);
        repository.save(workplace);
        return dto;
    }
}
