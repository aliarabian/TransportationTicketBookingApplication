package com.platform.business.model;

public abstract class SeatState {

    public abstract boolean isAvailable();

    public abstract SeatState book();

    public abstract SeatState hold();

    public abstract SeatState free();
}
