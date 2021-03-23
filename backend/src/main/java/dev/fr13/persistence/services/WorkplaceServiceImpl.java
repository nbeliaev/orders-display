package dev.fr13.persistence.services;

import dev.fr13.domain.Workplace;
import dev.fr13.dtos.WorkplaceDto;
import dev.fr13.persistence.reps.WorkplaceRepository;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceServiceImpl.class);

    private final Convertor<Workplace, WorkplaceDto> convertor;
    private final WorkplaceRepository repository;

    public WorkplaceServiceImpl(WorkplaceRepository repository,
                                @Qualifier("workplace") Convertor<Workplace, WorkplaceDto> convertor) {
        this.convertor = convertor;
        this.repository = repository;
    }

    @Override
    public List<WorkplaceDto> findAllActive() {
        log.debug("Find all active workplaces");
        var workplaces = repository.findAllActive();
        return workplaces.stream()
                .map(convertor::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Workplace> findByUuid(String uuid) {
        return repository.findByUuid(uuid);
    }

    @Override
    public WorkplaceDto save(WorkplaceDto dto) {
        var workplace = convertor.toEntity(dto);
        log.debug("Save {}", workplace);
        repository.save(workplace);
        return dto;
    }
}
