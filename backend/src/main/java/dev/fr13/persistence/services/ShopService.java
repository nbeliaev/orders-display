package dev.fr13.persistence.services;

import dev.fr13.dtos.ShopDto;

import java.util.List;

public interface ShopService {

    ShopDto save(ShopDto dto);

    List<ShopDto> findAll();
}
