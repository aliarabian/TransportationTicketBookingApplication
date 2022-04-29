package com.platform.business.enitity;

import java.time.LocalDate;

public class PlanePassenger extends Passenger {
    private Passport passportDetails;
    private LocalDate birthdate;


    public PlanePassenger(Long id, String firstName, String lastName, String nationalId, LocalDate birthdate, Passport passportDetails) {
        super(id, firstName, lastName, nationalId);
        this.birthdate = birthdate;
        this.passportDetails = passportDetails;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Passport getPassportDetails() {
        return passportDetails;
    }

    @Override
    public String toString() {
        return "PlanePassenger{" +
                "passportDetails=" + passportDetails +
                ", birthdate=" + birthdate +
                "} " + super.toString();
    }

}
