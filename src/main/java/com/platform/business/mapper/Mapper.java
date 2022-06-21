package com.platform.business.mapper;

public interface Mapper<T, U> {

    U toDto(T entity);
}
