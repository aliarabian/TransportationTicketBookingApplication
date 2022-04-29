package com.platform.enitity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Passport implements Serializable {
    private Long id;
    // Natural ID
    private String passportNO;
    private LocalDate expiresAt;
    private Country issuedIn;

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
