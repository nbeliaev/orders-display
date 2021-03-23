package dev.fr13.controllers;

import dev.fr13.dtos.WorkplaceDto;
import dev.fr13.persistence.services.WorkplaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkplaceRestController {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceRestController.class);

    private final WorkplaceService workplaceService;

    public WorkplaceRestController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping(path = "/api/v1/workplaces")
    public ResponseEntity<List<WorkplaceDto>> getWorkplaces() {
        log.debug("Get workplace list");
        var workplaces = workplaceService.findAllActive();
        return new ResponseEntity<>(workplaces, HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/workplaces")
    public ResponseEntity<WorkplaceDto> saveWorkplaces(
            @RequestBody WorkplaceDto workplaceDto) {
        log.debug("Save {}", workplaceDto);
        var workplace = workplaceService.save(workplaceDto);
        return new ResponseEntity<>(workplace, HttpStatus.CREATED);
    }
}
