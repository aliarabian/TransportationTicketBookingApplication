package com.transportation.airline.tickets.booking.boundry.dto;

import java.util.Set;

public class PlaneTicketBookingRequest {
    private Set<PlaneBookingPassengerDetail> passengersBookingDetails;
    private Long seatingSectionId;

    private Long customerId;
    private Long transportationId;

    public Set<PlaneBookingPassengerDetail> getPassengersBookingDetails() {
        return passengersBookingDetails;
    }

    public void setPassengersBookingDetails(Set<PlaneBookingPassengerDetail> passengersBookingDetails) {
        this.passengersBookingDetails = passengersBookingDetails;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTransportationId() {
        return transportationId;
    }

    public void setTransportationId(Long transportationId) {
        this.transportationId = transportationId;
    }

    public Long getSeatingSectionId() {
        return seatingSectionId;
    }

    public void setSeatingSectionId(Long seatingSectionId) {
        this.seatingSectionId = seatingSectionId;
    }

}