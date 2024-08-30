package com.tmax.datafabric.domain.outbox;

import com.tmax.datafabric.domain.event.Event;

public interface OutboxEventHandlerHelper {

    boolean support(OutboxEvent outboxEvent);

    void handle(Event domainEvent);
}
