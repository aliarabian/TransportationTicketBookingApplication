package com.platform.enitity;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Passport implements Serializable {
    private Long id;
    // Natural ID
    private String passportNO;
    private ZonedDateTime expiresAt;
    private Country issuedIn;

    public Passport(Long id, String passportNO, ZonedDateTime expiresAt, Country issuedIn) {
        this.id = id;
        this.passportNO = passportNO;
        this.expiresAt = expiresAt;
        this.issuedIn = issuedIn;
    }

    public String getPassportNO() {
        return passportNO;
    }

    public ZonedDateTime getExpiresAt() {
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
