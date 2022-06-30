package com.platform.business.booking.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class PlaneTicketBookingRequest {
    @NotNull
    @Size(min = 1)
    private Set<PlaneBookingPassengerDetail> passengersBookingDetails;

    @NotNull
    private Long seatingSectionId;

    private Set<Long> seatIds;

    public Set<PlaneBookingPassengerDetail> getPassengersBookingDetails() {
        return passengersBookingDetails;
    }

    public void setPassengersBookingDetails(Set<PlaneBookingPassengerDetail> passengersBookingDetails) {
        this.passengersBookingDetails = passengersBookingDetails;
    }

    public Long getSeatingSectionId() {
        return seatingSectionId;
    }

    public void setSeatingSectionId(Long seatingSectionId) {
        this.seatingSectionId = seatingSectionId;
    }

    public Set<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(Set<Long> seatIds) {
        this.seatIds = seatIds;
    }
    @Override
    public String toString() {
        return "PlaneTicketBookingRequest{" +
                "passengersBookingDetails=" + passengersBookingDetails +
                ", seatingSectionId=" + seatingSectionId +
                '}';
    }

}