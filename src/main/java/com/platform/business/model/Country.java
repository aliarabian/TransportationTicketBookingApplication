package com.platform.business.model;

import java.io.Serializable;

public class Country implements Serializable {
    private final Long id;
    private final String code;
    private final String name;


    public Country(Long id, String code, String name) {
        this.code = code;
        this.name = name;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
