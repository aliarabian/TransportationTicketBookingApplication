package com.platform.business.booking.dto;

import java.time.ZonedDateTime;
import java.util.Set;

public class FlightTicketDto {
    private Long ticketId;
    private Long transportationId;
    private String offset;
    private String destination;
    private ZonedDateTime departureDateTime;
    private String passportNO;
    private String passengerName;
    private int age;
    private Long sectionId;
    private String sectionTitle;
    private String seatNO;
    private Set<String> selectedPrivileges;

    public Long getTransportationId() {
        return transportationId;
    }

    public void setTransportationId(Long transportationId) {
        this.transportationId = transportationId;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ZonedDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(ZonedDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getPassportNO() {
        return passportNO;
    }

    public void setPassportNO(String passportNO) {
        this.passportNO = passportNO;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSeatNO() {
        return seatNO;
    }

    public void setSeatNO(String seatNO) {
        this.seatNO = seatNO;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Set<String> getSelectedPrivileges() {
        return selectedPrivileges;
    }

    public void setSelectedPrivileges(Set<String> selectedPrivileges) {
        this.selectedPrivileges = selectedPrivileges;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "PlaneTicketDto{" +
                "ticketId=" + ticketId +
                ", transportationId=" + transportationId +
                ", offset='" + offset + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", passportNO='" + passportNO + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", age=" + age +
                ", sectionId=" + sectionId +
                ", seatingSectionDescription='" + sectionTitle + '\'' +
                ", seatNO='" + seatNO + '\'' +
                ", selectedPrivileges=" + selectedPrivileges +
                '}';
    }
}
