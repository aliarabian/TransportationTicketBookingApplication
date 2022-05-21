package com.platform.business.service.booking.dto.request;

import java.time.LocalDate;

public class FlightPassengerDto {
    private String firstName;
    private String lastName;
    private String nationalIdNO;
    private LocalDate passportExpirationDate;
    private String passportIssuingCountryCode;
    private String passportNO;
    private LocalDate birthdate;

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

    public LocalDate getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(LocalDate passportExpirationDate) {
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
