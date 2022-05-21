package com.platform.business.model;

import java.io.Serializable;

public abstract class Seat implements Serializable {
    private Long id;
    private String seatNO;
    private boolean booked;

    public Seat(Long id, String seatNO) {
        this.id = id;
        this.seatNO = seatNO;
        this.booked = false;
    }

    public Long getId() {
        return id;
    }

    public String getSeatNO() {
        return seatNO;
    }

    public boolean isBooked() {
        return this.booked;
    }

    public void bookSeat() {
        this.booked = true;
    }


}
