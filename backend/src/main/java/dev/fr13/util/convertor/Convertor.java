package dev.fr13.util.convertor;

import java.util.List;

public interface Convertor<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    List<D> listEntitiesToListDtos(List<E> entities);

    List<E> listDtosToListEntities(List<D> dtos);
}
