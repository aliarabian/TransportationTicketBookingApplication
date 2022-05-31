package com.platform.business.service.booking.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class PlaneTicketBookingRequest {
    @NotNull
    private Set<PlaneBookingPassengerDetail> passengersBookingDetails;

    @NotNull
    private Long seatingSectionId;

    @NotNull
    private Long customerId;
    @NotNull
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

    @Override
    public String toString() {
        return "PlaneTicketBookingRequest{" +
                "passengersBookingDetails=" + passengersBookingDetails +
                ", seatingSectionId=" + seatingSectionId +
                ", customerId=" + customerId +
                ", transportationId=" + transportationId +
                '}';
    }
}