package com.platform.business.model;

import java.io.Serializable;

public abstract class Seat implements Serializable {
    private Long id;
    private String seatNO;
    private SeatState state;

    public Seat(Long id, String seatNO) {
        this.id = id;
        this.seatNO = seatNO;
        this.state = new FreeSeat();
    }

    public Long getId() {
        return id;
    }

    public String getSeatNO() {
        return seatNO;
    }

    public boolean isAvailable() {
        return this.state.isAvailable();
    }

    public void bookSeat() {
        this.state = this.state.book();
    }

    public void holdSeat() {
        this.state = this.state.hold();
    }

    public void free() {
        this.state = this.state.free();
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatNO='" + seatNO + '\'' +
                ", state=" + state +
                '}';
    }
}
