package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.domain.event.Event;
import com.tmax.datafabric.domain.outbox.OutboxEvent;

public interface OutboxEventHandlerHelper {
    boolean support(OutboxEvent outboxEvent);

    void handle(Event domainEvent);
}
