package dev.fr13.controllers;

import dev.fr13.dtos.OrderDto;
import dev.fr13.persistence.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {
    private static final Logger log = LoggerFactory.getLogger(OrderRestController.class);

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/api/v1/clients/{clientUuid}/shops/{shopUuid}/workplaces/{workplaceUuid}/orders")
    public ResponseEntity<List<OrderDto>> getOrders(
            @PathVariable String clientUuid,
            @PathVariable String shopUuid,
            @PathVariable String workplaceUuid) {

        log.debug("Get order list by client uuid {}, shop uuid {} and workplace uuid {}", clientUuid, shopUuid, workplaceUuid);
        var orders = orderService.findByClientAndShopAndWorkplace(clientUuid, shopUuid, workplaceUuid);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping(path = "/api/v1/clients/{clientUuid}/shops/{shopUuid}/workplaces/{workplaceUuid}/orders")
    public ResponseEntity<OrderDto> saveOrUpdateOrder(@RequestBody OrderDto dto) {
        log.debug("Received order {}", dto);
        var orderDto = orderService.saveOrUpdate(dto);
        return new ResponseEntity<>(orderDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/api/v1/clients/{clientUuid}/shops/{shopUuid}/workplaces/{workplaceUuid}/orders/{orderUuid}")
    public ResponseEntity<OrderDto> deleteOrder(
            @PathVariable String clientUuid,
            @PathVariable String shopUuid,
            @PathVariable String orderUuid) {
        log.debug("Delete order by uuid {}", orderUuid);
        var optnOrderDto = orderService.deleteByClientAndShopAndUuid(clientUuid, shopUuid, orderUuid);
        return new ResponseEntity<>(optnOrderDto.orElse(new OrderDto()), HttpStatus.OK);
    }
}
