package com.platform.business.model.booking;

import com.platform.business.model.*;
import com.platform.business.model.transportation.Flight;
import com.platform.business.model.transportation.FlightPassenger;
import com.platform.business.model.transportation.PlaneSeat;
import com.platform.business.model.transportation.SeatingSectionPrivilege;

import java.util.Set;

public class FlightTicket extends Ticket<Flight, FlightPassenger, PlaneSeat> {

    private Set<SeatingSectionPrivilege> selectedPrivileges;

    public FlightTicket(Flight transportation, Set<SeatingSectionPrivilege> privileges, FlightPassenger passenger, PlaneSeat seat, Customer customer) {
        super(transportation, passenger, seat, customer);
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
