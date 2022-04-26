package com.transportation.airline.tickets.booking.boundry.dto;

import java.util.Set;

public class PlaneBookingPassengerDetail {
    private Set<Long> seatingSectionPrivilegeIds;
    private PlanePassengerDto passenger;


    public Set<Long> getSeatingSectionPrivilegeIds() {
        return seatingSectionPrivilegeIds;
    }

    public void setSeatingSectionPrivilegeIds(Set<Long> seatingSectionPrivilegeIds) {
        this.seatingSectionPrivilegeIds = seatingSectionPrivilegeIds;
    }

    public PlanePassengerDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PlanePassengerDto passenger) {
        this.passenger = passenger;
    }
}