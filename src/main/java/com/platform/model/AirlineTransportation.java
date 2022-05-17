package com.platform.model;

import com.platform.business.booking.exception.BookingException;
import com.platform.business.booking.exception.SeatNotAvailableException;
import com.platform.business.booking.exception.SectionNotExistsException;

import java.time.OffsetDateTime;

public class AirlineTransportation extends Transportation<Plane, PlaneTicket> {

    public AirlineTransportation(Long id, Terminal offset, Terminal destination, OffsetDateTime departuresAt, Plane plane) {
        super(id, offset, destination, departuresAt, plane);
    }

    public PlaneSeat bookSeat(Long sectionClassId) throws BookingException {
        for (SeatingSection section : getVehicle().getSeatingSections()) {
            if (section.getId().equals(sectionClassId)) {
                return section.bookSeat()
                              .orElseThrow(() -> new SeatNotAvailableException("Seat Not Available"));
            }
        }
        throw new SectionNotExistsException("Wrong Section Number");
    }


    public int availableSeats() {
        return this.getVehicle().availableSeats();
    }

}

