package com.example.datafabric.application.outbox;

import com.example.datafabric.domain.event.TrainCreatedEvent;
import com.example.datafabric.domain.outbox.OutboxEvent;
import com.example.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.example.datafabric.domain.outbox.OuxboxEventRepository;
import java.util.HashSet;
import java.util.Set;
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
