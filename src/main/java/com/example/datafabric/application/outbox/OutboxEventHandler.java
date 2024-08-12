package com.example.datafabric.application.outbox;

import com.example.datafabric.domain.event.Event;
import com.example.datafabric.domain.outbox.OutboxEvent;
import com.example.datafabric.domain.outbox.OutboxEventHandlerHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxEventHandler {

    private final List<OutboxEventHandlerHelper> outboxEventHandlerHelperList;
    private final ObjectMapper objectMapper;

    @EventListener
    public void handle(OutboxEvent outboxEvent) {
        boolean handled = false;

        for (OutboxEventHandlerHelper outboxEventHandlerHelper : outboxEventHandlerHelperList) {
            if (outboxEventHandlerHelper.support(outboxEvent)) {
                Event domainEvent = convertToDomainEvent(outboxEvent);
                outboxEventHandlerHelper.handle(domainEvent);
                handled = true;
                break;
            }
        }

        if (!handled) {
            String errorMsg = "Matched outbox event helper does not exist";
            log.error(errorMsg);
        }
    }

    private Event convertToDomainEvent(OutboxEvent outboxEvent) {
        Event domainEvent = null;
        try {
            Class<?> clazz = Class.forName(outboxEvent.getEventType());
            Object object = objectMapper.readValue(outboxEvent.getPayload(), clazz);
            domainEvent = (Event) object;
        } catch (JsonProcessingException | ClassNotFoundException e) {
            String errorMsg = "fail to convert outbox event to domain event";
            log.error(errorMsg, e);
        }
        return domainEvent;
    }

}
