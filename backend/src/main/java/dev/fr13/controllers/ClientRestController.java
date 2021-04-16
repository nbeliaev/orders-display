package dev.fr13.controllers;

import dev.fr13.dtos.ClientDto;
import dev.fr13.persistence.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientRestController {
    private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = "/api/v1/clients")
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto client) {
        log.debug("Save client {}", client);
        var persisted = clientService.save(client);
        return new ResponseEntity<>(persisted, HttpStatus.CREATED);
    }
}
