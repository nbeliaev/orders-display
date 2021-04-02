package dev.fr13.util.convertor;

import dev.fr13.domain.Shop;
import dev.fr13.dtos.ShopDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("shop")
public class ShopConvertor implements Convertor<Shop, ShopDto> {
    private final ModelMapper mapper;

    public ShopConvertor(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Shop toEntity(ShopDto dto) {
        return mapper.map(dto, Shop.class);
    }

    @Override
    public ShopDto toDto(Shop entity) {
        return mapper.map(entity, ShopDto.class);
    }

    @Override
    public List<ShopDto> listEntitiesToListDtos(List<Shop> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Shop> listDtosToListEntities(List<ShopDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}