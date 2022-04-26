package com.transportation.airline.tickets.booking.entity;

import java.io.Serializable;
import java.util.Set;

public class PlaneTicket extends Ticket<AirlineTransportation, PlanePassenger, PlaneSeat>  {

    private Set<SeatingSectionPrivilege> selectedPrivileges;

    public PlaneTicket(Long id, AirlineTransportation transportation, Set<SeatingSectionPrivilege> privileges, PlanePassenger passenger, PlaneSeat seat, Customer customer) {
        super(id, transportation, passenger, seat, customer);
        this.selectedPrivileges = privileges;
    }

    public Set<SeatingSectionPrivilege> getSelectedPrivileges() {
        return selectedPrivileges;
    }

    @Override
    public String toString() {
        return "PlaneTicket{" +
                "selectedPrivileges=" + selectedPrivileges +
                "} " + super.toString();
    }
}
