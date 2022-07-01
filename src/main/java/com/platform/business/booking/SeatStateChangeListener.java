package com.platform.business.booking;

import com.platform.business.mapper.SeatingSectionMapper;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
@ApplicationScope
public class SeatStateChangeListener {
    private ConcurrentHashMap<Long, ConcurrentLinkedDeque<SseEmitter>> subscribersMap = new ConcurrentHashMap<>();
    private final SeatingSectionMapper seatingSectionMapper;

    public SeatStateChangeListener(SeatingSectionMapper seatingSectionMapper) {
        this.seatingSectionMapper = seatingSectionMapper;
    }

    @Async
    @EventListener
    public void handleSeatStateChangeEvent(SeatStateChangedEvent event) {
        ConcurrentLinkedDeque<SseEmitter> sseEmitters = subscribersMap.get(event.getSection().getId());
        if (sseEmitters != null) {
            sseEmitters.forEach(emitter -> {
                        try {
                            emitter.send(seatingSectionMapper.toDto(event.getSection()));
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    }
            );
        }
    }

    public void addSubscriber(Long sectionId, SseEmitter emitter) {
        emitter.onCompletion(() -> {
            getEmitters(sectionId).ifPresent(emitters -> emitters.remove(emitter));
        });
        emitter.onTimeout(() -> {
            emitter.complete();
            getEmitters(sectionId).ifPresent(emitters -> emitters.remove(emitter));
        });
        subscribersMap.computeIfAbsent(sectionId, (x) -> new ConcurrentLinkedDeque<>()).add(emitter);

    }

    Optional<ConcurrentLinkedDeque<SseEmitter>> getEmitters(long sectionId) {
        return Optional.ofNullable(subscribersMap.get(sectionId));
    }
}
