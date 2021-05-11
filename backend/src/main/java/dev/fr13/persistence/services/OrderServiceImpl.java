package dev.fr13.persistence.services;

import dev.fr13.domain.Order;
import dev.fr13.dtos.OrderDto;
import dev.fr13.exceptions.NoSuchClientException;
import dev.fr13.exceptions.NoSuchShopException;
import dev.fr13.exceptions.NoSuchWorkplaceException;
import dev.fr13.persistence.reps.OrderRepository;
import dev.fr13.util.OrderItemsProcessor;
import dev.fr13.util.convertor.Convertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository repository;
    private final ClientService clientService;
    private final ShopService shopService;
    private final WorkplaceService workplaceService;
    private final OrderItemsProcessor itemsProcessor;
    private final Convertor<Order, OrderDto> convertor;

    public OrderServiceImpl(OrderRepository repository,
                            ClientService clientService,
                            ShopService shopService,
                            WorkplaceService workplaceService,
                            OrderItemsProcessor itemsProcessor,
                            @Qualifier("order") Convertor<Order, OrderDto> convertor) {
        this.repository = repository;
        this.clientService = clientService;
        this.shopService = shopService;
        this.workplaceService = workplaceService;
        this.itemsProcessor = itemsProcessor;
        this.convertor = convertor;
    }

    @Override
    public List<OrderDto> findByClientAndShopAndWorkplace(String clientUuid, String shopUuid, String workplaceUuid) {
        var client = clientService.findByUuidAndActive(clientUuid)
                .orElseThrow(() -> new NoSuchClientException(clientUuid));
        var shop = shopService.findByUuidAndClientAndActive(shopUuid, client)
                .orElseThrow(() -> new NoSuchShopException(shopUuid));
        var workplace = workplaceService.findByUuidAndShopAndClient(workplaceUuid, shop, client)
                .orElseThrow(() -> new NoSuchWorkplaceException(workplaceUuid));

        log.debug("Find all orders by client {}, shop {} and workplace {}",
                client.getName(), shop.getName(), workplace.getName());
        var orders = repository.findAllByClientAndShop(
                client, shop, Sort.by(Sort.Direction.ASC, "timestamp"));

        var result = new ArrayList<Order>();
        for (Order order : orders) {
            var isThereSuitableItems = order.getItems().stream()
                    .anyMatch(i -> i.getWorkplace().equals(workplace));
            if (isThereSuitableItems) {
                var tempOrder = new Order(order);
                var items = order.getItems().stream()
                        .filter(i -> i.getWorkplace().equals(workplace))
                        .collect(Collectors.toCollection(ArrayList::new));
                tempOrder.setItems(items);
                result.add(tempOrder);
            }
        }
        return convertor.listEntitiesToListDtos(result);
    }

    @Override
    public OrderDto save(OrderDto dto) {
        var order = convertor.toEntity(dto);
        repository.findByUuidAndClientAndShop(dto.getUuid(), order.getClient(), order.getShop())
                .ifPresentOrElse(
                        i -> {
                            order.setId(i.getId());
                            itemsProcessor.refillStatusesAndRowsNumbers(i, order);
                        },
                        () -> itemsProcessor.setStatusesAndRowsNumbersInNewOrder(order)
                );
        log.debug("Save order {}", order);
        var persisted = repository.save(order);
        return convertor.toDto(persisted);
    }

    @Override
    public Optional<OrderDto> deleteByClientAndShopAndUuid(String clientUuid, String shopUuid, String orderUuid) {
        var client = clientService.findByUuidAndActive(clientUuid)
                .orElseThrow(() -> new NoSuchClientException(clientUuid));
        var shop = shopService.findByUuidAndClientAndActive(shopUuid, client)
                .orElseThrow(() -> new NoSuchShopException(shopUuid));

        log.debug("Delete order by client {}, shop {} and uuid {}", client.getName(), shop.getName(), orderUuid);
        var optnOrder = repository.deleteByUuidAndClientAndShop(orderUuid, client, shop);
        return optnOrder.map(convertor::toDto);
    }
}
