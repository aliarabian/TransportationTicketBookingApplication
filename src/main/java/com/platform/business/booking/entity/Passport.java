package com.platform.business.booking.entity;

import com.platform.business.model.Country;

import java.io.Serializable;
import java.time.LocalDate;

public class Passport implements Serializable {
    private final Long id;
    // Natural ID
    private final String passportNO;
    private final LocalDate expiresAt;
    private final Country issuedIn;

    public Passport(Long id, String passportNO, LocalDate expiresAt, Country issuedIn) {
        this.id = id;
        this.passportNO = passportNO;
        this.expiresAt = expiresAt;
        this.issuedIn = issuedIn;
    }

    public String getPassportNO() {
        return passportNO;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public Country getIssuedIn() {
        return issuedIn;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", passportNO='" + passportNO + '\'' +
                ", expiresAt=" + expiresAt +
                ", issuedIn=" + issuedIn +
                '}';
    }
}
