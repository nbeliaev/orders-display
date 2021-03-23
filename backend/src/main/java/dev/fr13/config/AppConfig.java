package dev.fr13.config;

import dev.fr13.domain.OrderItem;
import dev.fr13.dtos.OrderItemDto;
import org.modelmapper.Conditions;
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
                        src -> src.getWorkplace().getUuid(),
                        OrderItemDto::setWorkplace);
        return mapper;
    }
}
