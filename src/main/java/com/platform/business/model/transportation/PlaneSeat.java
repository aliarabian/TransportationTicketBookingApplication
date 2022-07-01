package com.platform.business.model.transportation;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlaneSeat extends Seat {
    @JsonIgnore
    private SeatingSection section;


    public PlaneSeat(Long id, String seatNO, SeatingSection section) {
        super(id, seatNO);
        this.section = section;
        this.state = new FreeSeat();
    }


    public SeatingSection getSection() {
        return section;
    }

}
