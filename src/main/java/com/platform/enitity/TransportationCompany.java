package com.platform.enitity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TransportationCompany<T extends Transportation<?,?>, U extends TransportationVehicle<?>> implements Serializable {
    private Long id;
    private String name;
    private City city;
    private Set<T> transportations;
    private Set<U> vehicles;

    public TransportationCompany(Long id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.transportations = new HashSet<>();
        this.vehicles = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public Set<T> getTransportations() {
        return transportations;
    }

    public void addTransportation(T transportation) {
        Objects.requireNonNull(transportation);
        transportations.add(transportation);
    }

    public Set<U> getVehicles() {
        return vehicles;
    }

    public void addVehicle(U newVehicle) {
        Objects.requireNonNull(newVehicle);
        this.vehicles.add(newVehicle);
    }

    @Override
    public String toString() {
        return "TransportationCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + city +
                '}';
    }
}
