package com.platform.business.mapper;

public interface Mapper<T, U> {
    T fromDto(U dto);

    U toDto(T entity);
}
