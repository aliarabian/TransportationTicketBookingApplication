package com.transportation.airline.tickets.booking.entity;

public class PlaneSeat extends Seat {
    private SeatingSection section;


    public PlaneSeat(Long id, String seatNO, SeatingSection section) {
        super(id, seatNO);
        this.section = section;
    }


    public SeatingSection getSection() {
        return section;
    }

}
