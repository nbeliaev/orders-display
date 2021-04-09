package dev.fr13.util.convertor;

import dev.fr13.domain.Order;
import dev.fr13.dtos.OrderDto;
import dev.fr13.exceptions.NoSuchClientException;
import dev.fr13.exceptions.NoSuchShopException;
import dev.fr13.exceptions.NoSuchWorkplaceException;
import dev.fr13.persistence.services.ClientService;
import dev.fr13.persistence.services.ShopService;
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
    private final ClientService clientService;
    private final ShopService shopService;

    public OrderConverter(ModelMapper mapper,
                          WorkplaceService workplaceService,
                          ClientService clientService,
                          ShopService shopService) {
        this.mapper = mapper;
        this.workplaceService = workplaceService;
        this.clientService = clientService;
        this.shopService = shopService;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        var entity = mapper.map(dto, Order.class);
        var client = clientService.findByUuidAndActive(dto.getClient())
                .orElseThrow(() -> new NoSuchClientException(dto.getClient()));
        var shop = shopService.findByUuidAndClientAndActive(dto.getShop(), client)
                .orElseThrow(() -> new NoSuchShopException(dto.getShop()));
        entity.setClient(client);
        entity.setShop(shop);
        mapWorkplace(dto, entity);
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
            var workplace = workplaceService.findByUuidAndShopAndClient(
                    workplaceUuid,
                    dstn.getShop(),
                    dstn.getClient())
                    .orElseThrow(() -> new NoSuchWorkplaceException(workplaceUuid));
            items.get(i).setWorkplace(workplace);
        }
    }
}
