package com.platform.business.service.booking.dto.request;

import java.util.Set;

public class PlaneBookingPassengerDetail {
    private Set<Long> seatingSectionPrivilegeIds;
    private FlightPassengerDto passenger;


    public Set<Long> getSeatingSectionPrivilegeIds() {
        return seatingSectionPrivilegeIds;
    }

    public void setSeatingSectionPrivilegeIds(Set<Long> seatingSectionPrivilegeIds) {
        this.seatingSectionPrivilegeIds = seatingSectionPrivilegeIds;
    }

    public FlightPassengerDto getPassenger() {
        return passenger;
    }

    public void setPassenger(FlightPassengerDto passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "PlaneBookingPassengerDetail{" +
                "seatingSectionPrivilegeIds=" + seatingSectionPrivilegeIds +
                ", passenger=" + passenger +
                '}';
    }
}
