package com.tmax.datafabric.application.outbox;

import com.tmax.datafabric.domain.event.TrainCreatedEvent;
import com.tmax.datafabric.domain.outbox.OutboxEvent;
import com.tmax.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.tmax.datafabric.domain.outbox.OuxboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainEventToOutboxEventHandler {

    private final OuxboxEventRepository ouxboxEventRepository;

    @EventListener
    public void handle(TrainCreatedEvent trainCreatedEvent) {
        OutboxEvent outboxEvent = OutboxEvent.builder()
            .aggregateType(AggregateType.DATAFABRIC_TRAIN)
            .eventType(TrainCreatedEvent.class.getName())
            .payload(trainCreatedEvent.getPayload())
            .build();

        ouxboxEventRepository.save(outboxEvent);
    }

}
