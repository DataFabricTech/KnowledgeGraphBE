package com.example.datafabric.application.outbox;

import com.example.datafabric.domain.event.Event;
import com.example.datafabric.domain.outbox.OutboxEvent;
import com.example.datafabric.domain.outbox.OutboxEventHandlerHelper;

public class InferenceCreatedOutboxEventHandlerHelper implements OutboxEventHandlerHelper {

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return false;
    }

    @Override
    public void handle(Event domainEvent) {

    }
}
