package com.platform.business.booking.entity;

import java.io.Serializable;

public abstract class Passenger implements Serializable {
    private final Long id;
    private final String firstName;
    private final String lastName;
    // Natural ID - Unique
    private final String nationalId;

    protected Passenger(Long id, String firstName, String lastName, String nationalId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                '}';
    }
}
