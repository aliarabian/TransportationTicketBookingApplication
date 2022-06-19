package com.platform.security;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JwtBlackListService {
    private final ConcurrentHashMap<String, LocalDateTime> jwtBlackListMap = new ConcurrentHashMap<>();

    public boolean contains(String token) {
        return jwtBlackListMap.containsKey(token);
    }

    public void add(String token) {
        jwtBlackListMap.put(token, LocalDateTime.now());

    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void scheduledRemove() {
        jwtBlackListMap.values()
                       .removeIf(createdAt -> Duration.between(createdAt, LocalDateTime.now()).toHours() >= 24);
    }
}
