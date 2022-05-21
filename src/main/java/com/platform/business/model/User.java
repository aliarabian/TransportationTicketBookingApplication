package com.platform.business.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private Long id;
    // Unique
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User(Long id, String username, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }
}
