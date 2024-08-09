package com.example.datafabric.domain.outbox;

import com.example.datafabric.domain.event.Event;

public interface OutboxEventHandlerHelper {

    boolean support(OutboxEvent outboxEvent);

    void handle(Event domainEvent);
}
