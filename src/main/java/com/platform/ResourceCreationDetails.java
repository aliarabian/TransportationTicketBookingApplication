package com.platform;

import java.net.URI;
import java.time.Instant;

public class ResourceCreationDetails {
    private final URI location;
    private final Instant timestamp;

    public ResourceCreationDetails(String location) {
        this.location = URI.create(location);
        timestamp = Instant.now();
    }

    public URI getLocation() {
        return location;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
