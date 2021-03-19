package dev.fr13.controllers;

import dev.fr13.dtos.OrderDto;
import dev.fr13.exceptions.NoSuchWorkplaceException;
import dev.fr13.persistence.services.OrderService;
import dev.fr13.persistence.services.WorkplaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {
    private static final Logger log = LoggerFactory.getLogger(OrderRestController.class);

    private final WorkplaceService workplaceService;
    private final OrderService orderService;

    public OrderRestController(WorkplaceService workplaceService, OrderService orderService) {
        this.workplaceService = workplaceService;
        this.orderService = orderService;
    }

    @GetMapping(path = "/api/v1/orders")
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestParam(required = false, defaultValue = "", name = "filter") String workplaceUuid) {

        log.debug("Get order list for workplace with uuid {}", workplaceUuid);

        if (workplaceUuid.isEmpty()) {
            var orders = orderService.findAll();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            var workplace = workplaceService.findByUuid(workplaceUuid)
                    .orElseThrow(() -> new NoSuchWorkplaceException(workplaceUuid));
            var orders = orderService.findAllByWorkplace(workplace);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/api/v1/orders")
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto dto) {
        log.debug("Save order {}", dto);
        var orderDto = orderService.saveOrUpdate(dto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/api/v1/orders")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto dto) {
        log.debug("Update order {}", dto);
        var orderDto = orderService.saveOrUpdate(dto);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/v1/orders/{uuid}")
    public ResponseEntity<String> deleteOrder(@PathVariable String uuid) {
        log.debug("Delete order with uuid {}", uuid);
        orderService.deleteByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
