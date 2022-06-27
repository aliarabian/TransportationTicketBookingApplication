package com.platform.business.model;

public class FreeSeat extends SeatState {
    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public SeatState book() {
        return new BookedSeat();
    }

    @Override
    public SeatState hold() {
        return new OnHoldSeat();
    }

    @Override
    public SeatState free() {
        return this;
    }

    @Override
    public String toString() {
        return "FreeSeat";
    }
}
