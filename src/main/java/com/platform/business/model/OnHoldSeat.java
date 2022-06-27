package com.platform.business.model;

public class OnHoldSeat extends SeatState {
    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public SeatState book() {
        return new BookedSeat();
    }

    @Override
    public SeatState hold() {
        return this;
    }

    @Override
    public SeatState free() {
        return new FreeSeat();
    }

    @Override
    public String toString() {
        return "OnHold";
    }
}
