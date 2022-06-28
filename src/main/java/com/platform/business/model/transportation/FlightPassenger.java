package com.platform.business.model.transportation;

import com.platform.business.model.booking.Passenger;
import com.platform.business.model.booking.Passport;

import java.time.LocalDate;

public class FlightPassenger extends Passenger {
    private Passport passportDetails;
    private LocalDate birthdate;


    public FlightPassenger(Long id, String firstName, String lastName, String nationalId, LocalDate birthdate, Passport passportDetails) {
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
