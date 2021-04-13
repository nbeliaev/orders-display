package dev.fr13.controllers;

import dev.fr13.dtos.ShopDto;
import dev.fr13.persistence.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopRestController {
    private static final Logger log = LoggerFactory.getLogger(ShopRestController.class);

    private final ShopService shopService;

    public ShopRestController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping(path = "/api/v1/clients/{clientUuid}/shops")
    public ResponseEntity<ShopDto> saveShop(@RequestBody ShopDto shop) {
        log.debug("Save shop {}", shop);
        var persisted = shopService.save(shop);
        return new ResponseEntity<>(persisted, HttpStatus.OK);
    }

    @GetMapping(path = "/api/v1/clients/{clientUuid}/shops")
    public ResponseEntity<List<ShopDto>> getShops(@PathVariable String clientUuid) {
        log.debug("Get shops list by client uuid {}", clientUuid);
        var shops = shopService.findAllByClientUuid(clientUuid);
        return new ResponseEntity<>(shops, HttpStatus.OK);
    }
}
