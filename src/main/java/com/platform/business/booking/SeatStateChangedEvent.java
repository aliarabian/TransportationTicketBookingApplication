package com.platform.business.booking;


import com.platform.business.model.transportation.SeatingSection;

import java.time.LocalDateTime;

public class SeatStateChangedEvent {
    private final SeatingSection section;
    private final LocalDateTime timestamp;

    public SeatStateChangedEvent(SeatingSection section) {
        this.section = section;
        this.timestamp = LocalDateTime.now();
    }

    public SeatingSection getSection() {
        return section;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SeatStateChangedEvent{" +
                "section=" + section.title() +
                ", timestamp=" + timestamp +
                '}';
    }
}
