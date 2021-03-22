package dev.fr13.persistence.services;

import dev.fr13.domain.Order;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderDto;
import dev.fr13.dtos.OrderItemDto;
import dev.fr13.exceptions.NoSuchWorkplaceException;
import dev.fr13.persistence.reps.OrderRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ModelMapper mapper;
    private final WorkplaceService workplaceService;
    private final OrderRepository repository;

    public OrderServiceImpl(ModelMapper mapper, WorkplaceService workplaceService, OrderRepository repository) {
        this.mapper = mapper;
        this.workplaceService = workplaceService;
        this.repository = repository;
    }

    @Override
    public List<OrderDto> findByWorkplace(Workplace workplace) {
        log.debug("Find all orders by workplace {}", workplace);
        var orders = repository.findByWorkplace(workplace);
        return convertToDto(orders);
    }

    @Override
    public List<OrderDto> findAll() {
        log.debug("Find all orders");
        var orders = repository.findAll();
        return convertToDto(orders);
    }

    @Override
    public OrderDto saveOrUpdate(OrderDto dto) {
        log.debug("Save {}", dto);
        var order = mapper.map(dto, Order.class);
        mapWorkplaces(dto, order);
        repository.save(order);
        return dto;
    }

    @Override
    public void deleteByUuid(String uuid) {
        log.debug("Delete by uuid {}", uuid);
        repository.deleteByUuid(uuid);
    }

    private void mapWorkplaces(OrderDto src, Order dstn) {
        var items = dstn.getItems();
        var itemsDto = src.getItems();
        for (int i = 0; i < items.size(); i++) {
            var workplaceUuid = itemsDto.get(i).getWorkplace();
            var workplace = workplaceService.findByUuid(
                    workplaceUuid)
                    .orElseThrow(() -> new NoSuchWorkplaceException(workplaceUuid));
            items.get(i).setWorkplace(workplace);
        }
    }

    private List<OrderDto> convertToDto(List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    var orderDto = mapper.map(order, OrderDto.class);
                    var itemsDto = order.getItems().stream()
                            .map(i -> mapper.map(i, OrderItemDto.class))
                            .collect(Collectors.toList());
                    orderDto.setItems(itemsDto);
                    return orderDto;
                })
                .sorted(Comparator.comparingLong(OrderDto::getTimestamp))
                .collect(Collectors.toList());
    }
}
