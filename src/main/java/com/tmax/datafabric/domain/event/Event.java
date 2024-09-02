package com.tmax.datafabric.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;

public abstract class Event {

    private final Instant occurredOn;

    public Event() {
        occurredOn = Instant.now();
    }

    @JsonIgnore
    public String getPayload() {
        JsonNode jsonNode = new ObjectMapper().valueToTree(this);
        return jsonNode.toString();
    }
}
