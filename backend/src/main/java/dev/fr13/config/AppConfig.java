package dev.fr13.config;

import dev.fr13.domain.OrderItem;
import dev.fr13.domain.OrderItemStatus;
import dev.fr13.domain.Shop;
import dev.fr13.domain.Workplace;
import dev.fr13.dtos.OrderItemDto;
import dev.fr13.dtos.ShopDto;
import dev.fr13.dtos.WorkplaceDto;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull())
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        mapper.createTypeMap(OrderItem.class, OrderItemDto.class)
                .addMapping(
                        OrderItem::getWorkplaceUuid,
                        OrderItemDto::setWorkplace)
                .addMapping(OrderItem::getStatusName,
                        OrderItemDto::setStatus);

        mapper.createTypeMap(OrderItemDto.class, OrderItem.class)
                .addMappings(m -> m.skip(OrderItem::setStatus))
                .setPostConverter(toEntityConvertor());

        mapper.createTypeMap(Shop.class, ShopDto.class)
                .addMapping(
                        Shop::getClientUuid,
                        ShopDto::setClient
                );

        mapper.createTypeMap(Workplace.class, WorkplaceDto.class)
                .addMapping(
                        Workplace::getShopUuid,
                        WorkplaceDto::setShop
                )
                .addMapping(
                        Workplace::getClientUuid,
                        WorkplaceDto::setClient);

        return mapper;
    }

    private Converter<OrderItemDto, OrderItem> toEntityConvertor() {
        return context -> {
            var source = context.getSource();
            var destination = context.getDestination();
            mapStatuses(source, destination);
            return destination;
        };
    }

    private void mapStatuses(OrderItemDto source, OrderItem destination) {
        var statusName = source.getStatus();
        var status = OrderItemStatus.getStatusByName(statusName);
        destination.setStatus(status);
    }
}
