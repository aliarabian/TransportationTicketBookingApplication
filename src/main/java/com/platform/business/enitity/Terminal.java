package com.platform.business.enitity;

import java.io.Serializable;

public class Terminal implements Serializable {
    private Long id;
    private String name;
    private City city;

    public Terminal(Long id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + city +
                '}';
    }
}
