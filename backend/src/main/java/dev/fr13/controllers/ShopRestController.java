package dev.fr13.controllers;

import dev.fr13.dtos.ShopDto;
import dev.fr13.persistence.services.ShopService;
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
public class ShopRestController {
    private static final Logger log = LoggerFactory.getLogger(ShopRestController.class);

    private final ShopService shopService;

    public ShopRestController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping(path = "/api/v1/shops")
    public ResponseEntity<ShopDto> saveShop(@RequestBody ShopDto shop) {
        log.debug("Save shop {}", shop);
        var persisted = shopService.save(shop);
        return new ResponseEntity<>(persisted, HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/v1/shops")
    public ResponseEntity<List<ShopDto>> getShops() {
        log.debug("Get shops list");
        var shops = shopService.findAll();
        return new ResponseEntity<>(shops, HttpStatus.OK);
    }
}
