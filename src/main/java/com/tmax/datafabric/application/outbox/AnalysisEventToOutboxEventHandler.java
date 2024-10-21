package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.domain.event.AnalysisCompletedEvent;
import com.tmax.datafabric.domain.event.AnalysisCreatedEvent;
import com.tmax.datafabric.domain.event.AnalysisFailedEvent;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.tmax.datafabric.domain.outbox.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalysisEventToOutboxEventHandler {

    private final OutboxEventRepository outboxEventRepository;

    @EventListener
    public void handle(AnalysisCreatedEvent analysisCreatedEvent) {
        OutboxEvent outboxEvent = OutboxEvent.builder()
            .aggregateType(AggregateType.DATAFABRIC_ANALYSIS)
            .eventType(AnalysisCreatedEvent.class.getName())
            .payload(analysisCreatedEvent.getPayload())
            .build();

        outboxEventRepository.save(outboxEvent);
    }

    @EventListener
    public void handle(AnalysisCompletedEvent analysisCompletedEvent) {
        OutboxEvent outboxEvent = OutboxEvent.builder()
            .aggregateType(AggregateType.DATAFABRIC_ANALYSIS)
            .eventType(AnalysisCompletedEvent.class.getName())
            .payload(analysisCompletedEvent.getPayload())
            .build();

        outboxEventRepository.save(outboxEvent);
    }

    @EventListener
    public void handle(AnalysisFailedEvent analysisFailedEvent) {
        OutboxEvent outboxEvent = OutboxEvent.builder()
            .aggregateType(AggregateType.DATAFABRIC_ANALYSIS)
            .eventType(AnalysisFailedEvent.class.getName())
            .payload(analysisFailedEvent.getPayload())
            .build();

        outboxEventRepository.save(outboxEvent);
    }
}
