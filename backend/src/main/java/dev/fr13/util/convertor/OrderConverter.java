package dev.fr13.util.convertor;

import dev.fr13.domain.Order;
import dev.fr13.domain.OrderItemStatus;
import dev.fr13.dtos.OrderDto;
import dev.fr13.exceptions.NoSuchWorkplaceException;
import dev.fr13.persistence.services.WorkplaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("order")
public class OrderConverter implements Convertor<Order, OrderDto> {
    private final ModelMapper mapper;
    private final WorkplaceService workplaceService;

    public OrderConverter(ModelMapper mapper, WorkplaceService workplaceService) {
        this.mapper = mapper;
        this.workplaceService = workplaceService;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        var entity = mapper.map(dto, Order.class);
        mapWorkplace(dto, entity);
        mapStatuses(dto, entity);
        return entity;
    }

    @Override
    public OrderDto toDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> listEntitiesToListDtos(List<Order> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> listDtosToListEntities(List<OrderDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    private void mapWorkplace(OrderDto src, Order dstn) {
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

    // TODO try to use convertor
    private void mapStatuses(OrderDto src, Order dstn) {
        var items = dstn.getItems();
        var itemsDto = src.getItems();
        for (int i = 0; i < items.size(); i++) {
            var statusName = itemsDto.get(i).getStatus();
            var status = OrderItemStatus.getStatusByName(statusName);
            items.get(i).setStatus(status);
        }
    }
}
