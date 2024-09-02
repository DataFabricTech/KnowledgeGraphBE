package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.domain.event.Event;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventHandlerHelper;

public class InferenceCreatedOutboxEventHandlerHelper implements OutboxEventHandlerHelper {

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return false;
    }

    @Override
    public void handle(Event domainEvent) {

    }
}
