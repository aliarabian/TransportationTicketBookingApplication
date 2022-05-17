package com.platform.business.mapper;

/**
 * TODO FIX redesign Mapper interface and make it two separate interfaces one for mapping to and another for mapping from dto Or introduce two other mappers
 */
public interface Mapper<T, U> {
    T fromDto(U dto);

    U toDto(T entity);
}
