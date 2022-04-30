package com.platform.business.enitity;

import com.platform.business.service.booking.exception.BookingException;

import java.time.OffsetDateTime;

public class AirlineTransportation extends Transportation<Plane, PlaneTicket> {

    public AirlineTransportation(Long id, Terminal offset, Terminal destination, OffsetDateTime departuresAt, Plane plane) {
        super(id, offset, destination, departuresAt, plane);
    }

    public PlaneSeat bookSeat(Long sectionClassId) throws BookingException {
        for (SeatingSection section : getVehicle().getSeatingSections()) {
            if (section.getId().equals(sectionClassId)) {
                return section.bookSeat()
                              .orElseThrow(BookingException::new);
            }
        }
        throw new BookingException();
    }


    public int availableSeats() {
        return this.getVehicle().availableSeats();
    }

}

