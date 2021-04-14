package dev.fr13.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForwardController {
    private static final Logger log = LoggerFactory.getLogger(ForwardController.class);

    @GetMapping(path = "/clients/{clientUuid}/**")
    public String forward(HttpServletRequest request) {
        log.debug("Forward to {}", request.getRequestURI());
        return "forward:/";
    }
}