package com.platform.business.model.transportation;

public class BookedSeat extends SeatState {
    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public SeatState book() {
        return this;
    }

    @Override
    public SeatState hold() {
        return new OnHoldSeat();
    }

    @Override
    public SeatState free() {
        return new FreeSeat();
    }

    @Override
    public String toString() {
        return "BookedSeat";
    }
}
