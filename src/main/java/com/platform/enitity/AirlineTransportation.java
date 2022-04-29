package com.platform.enitity;

import java.time.ZonedDateTime;

public class AirlineTransportation extends Transportation<Plane, PlaneTicket> {

    public AirlineTransportation(Long id, Terminal offset, Terminal destination, ZonedDateTime departuresAt, Plane plane) {
        super(id, offset, destination, departuresAt, plane);
    }

    public PlaneSeat bookSeat(Long sectionClassId) throws Exception {
        for (SeatingSection section : getVehicle().getSeatingSections()) {
            if (section.getId().equals(sectionClassId)) {
                return section.bookSeat().orElseThrow();
            }
        }
        throw new Exception();
    }


    public int availableSeats() {
        return this.getVehicle().availableSeats();
    }

}

