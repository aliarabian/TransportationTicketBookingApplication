package com.platform.business.booking.dto;

import java.time.ZonedDateTime;

public class PlanePassengerDto {
    private String firstName;
    private String lastName;
    private String nationalIdNO;
    private ZonedDateTime passportExpirationDate;
    private String passportIssuingCountryCode;
    private String passportNO;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalIdNO() {
        return nationalIdNO;
    }

    public void setNationalIdNO(String nationalIdNO) {
        this.nationalIdNO = nationalIdNO;
    }

    public ZonedDateTime getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(ZonedDateTime passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    public String getPassportIssuingCountryCode() {
        return passportIssuingCountryCode;
    }

    public void setPassportIssuingCountryCode(String passportIssuingCountryCode) {
        this.passportIssuingCountryCode = passportIssuingCountryCode;
    }

    public String getPassportNO() {
        return passportNO;
    }

    public void setPassportNO(String passportNO) {
        this.passportNO = passportNO;
    }

}
