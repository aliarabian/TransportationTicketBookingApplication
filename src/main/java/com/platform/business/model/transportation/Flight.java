package com.platform.business.model.transportation;

import com.platform.business.booking.entity.FlightTicket;
import com.platform.business.booking.exception.BookingException;
import com.platform.business.booking.exception.SeatNotAvailableException;
import com.platform.business.booking.exception.SectionNotExistsException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Flight extends Transportation<Plane, FlightTicket> {

    public Flight(Long id, Terminal offset, Terminal destination, OffsetDateTime departuresAt, Plane plane) {
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

    public List<PlaneSeat> bookSeats(Long seatingSectionId, Set<Long> seatIds) {
        List<PlaneSeat> seats = new ArrayList<>();
        for (SeatingSection section : getVehicle().getSeatingSections()) {
            if (section.getId().equals(seatingSectionId)) {
                for (Long seatId : seatIds) {
                    PlaneSeat seat = section.bookSeat(seatId)
                                            .orElseThrow(() -> new SeatNotAvailableException("Seat Not Available"));
                    seats.add(seat);
                }
                return seats;
            }
        }
        throw new SectionNotExistsException("Wrong Section Number");
    }
}

