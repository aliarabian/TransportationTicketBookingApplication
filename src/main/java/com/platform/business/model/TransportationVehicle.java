package com.platform.business.model;

import java.io.Serializable;

public abstract class TransportationVehicle<T extends TransportationCompany<?, ?>> implements Serializable {
    private Long id;
    private int capacity;
    private String modelName;
    private final T transportationCompany;

    public TransportationVehicle(Long id, int capacity, String modelName, T transportationCompany) {
        this.id = id;
        this.capacity = capacity;
        this.modelName = modelName;
        this.transportationCompany = transportationCompany;
    }


    public Long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getModelName() {
        return modelName;
    }

    public T getTransportationCompany() {
        return transportationCompany;
    }

    @Override
    public String toString() {
        return "TransportationVehicle{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", modelName='" + modelName + '\'' +
                '}';
    }

}
