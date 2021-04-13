package dev.fr13.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    private static final Logger log = LoggerFactory.getLogger(ShopController.class);

    @GetMapping(path = "/clients/{clientUuid}/shops")
    public String getShops() {
        log.debug("Get shops");
        return "forward:/";
    }
}