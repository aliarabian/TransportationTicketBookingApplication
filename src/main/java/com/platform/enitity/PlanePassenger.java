package com.platform.enitity;

public class PlanePassenger extends Passenger {
    private Passport passportDetails;


    public PlanePassenger(Long id, String firstName, String lastName, String nationalId, Passport passportDetails) {
        super(id, firstName, lastName, nationalId);
        this.passportDetails = passportDetails;
    }

    public Passport getPassportDetails() {
        return passportDetails;
    }

    @Override
    public String toString() {
        return "PlanePassenger{" +
                "passportDetails=" + passportDetails +
                "} " + super.toString();
    }
}
