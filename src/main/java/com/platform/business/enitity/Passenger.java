package com.platform.business.enitity;

import java.io.Serializable;

public abstract class Passenger implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    // Natural ID - Unique
    private String nationalId;

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
