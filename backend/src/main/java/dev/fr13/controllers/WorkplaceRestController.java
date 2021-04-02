package dev.fr13.controllers;

import dev.fr13.dtos.WorkplaceDto;
import dev.fr13.persistence.services.WorkplaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkplaceRestController {
    private static final Logger log = LoggerFactory.getLogger(WorkplaceRestController.class);

    private final WorkplaceService workplaceService;

    public WorkplaceRestController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping(path = "/api/v1/clients/{clientId}/shops/{shopId}/workplaces")
    public ResponseEntity<List<WorkplaceDto>> getWorkplaces(@PathVariable String clientId,
                                                            @PathVariable String shopId) {
        log.debug("Get workplace list");
        var workplaces = workplaceService.findAllActiveByShopIdAndClientId(shopId, clientId);
        return new ResponseEntity<>(workplaces, HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/clients/{clientId}/shops/{shopId}/workplaces")
    public ResponseEntity<WorkplaceDto> saveWorkplace(@RequestBody WorkplaceDto workplaceDto) {
        log.debug("Save workplace {}", workplaceDto);
        var workplace = workplaceService.save(workplaceDto);
        return new ResponseEntity<>(workplace, HttpStatus.CREATED);
    }
}
