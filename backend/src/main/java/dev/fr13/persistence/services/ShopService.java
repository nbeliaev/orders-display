package dev.fr13.persistence.services;

import dev.fr13.domain.Client;
import dev.fr13.domain.Shop;
import dev.fr13.dtos.ShopDto;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    ShopDto save(ShopDto dto);

    List<ShopDto> findAllByClientId();

    Optional<Shop> findByUuidAndClient(String uuid, Client client);
}
