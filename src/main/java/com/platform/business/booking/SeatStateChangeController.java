package com.platform.business.booking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;

@RestController
@RequestMapping("flights")
public class SeatStateChangeController {

    private final SeatStateChangeListener seatStateChangeListener;

    public SeatStateChangeController(SeatStateChangeListener seatStateChangeListener) {
        this.seatStateChangeListener = seatStateChangeListener;
    }

    @GetMapping(value = "{flightId}/sections/{sectionId}")
    public SseEmitter subscribeToSeatStateChanges(@PathVariable("sectionId") @Valid Long sectionId) {
        SseEmitter emitter = new SseEmitter(86400000L);
        seatStateChangeListener.addSubscriber(sectionId, emitter);
        return emitter;
    }

}
