package com.transportation.airline.tickets.booking.entity;

import java.io.Serializable;

public class City implements Serializable {
    private Long id;
    private Country country;
    private String name;

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
