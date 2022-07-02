package com.platform.business.model;

import java.io.Serializable;

public class City implements Serializable {
    private final Long id;
    private final Country country;
    private final String name;

    public City(Long id, String name, Country country) {
        this.id = id;
        this.country = country;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", country=" + country +
                ", name='" + name + '\'' +
                '}';
    }
}
