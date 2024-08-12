package com.example.datafabric.application.outbox;

import com.example.datafabric.domain.event.Event;
import com.example.datafabric.domain.event.TrainCreatedEvent;
import com.example.datafabric.domain.outbox.OutboxEvent;
import com.example.datafabric.domain.outbox.OutboxEventConstant.AggregateType;
import com.example.datafabric.domain.outbox.OutboxEventHandlerHelper;
import com.example.datafabric.domain.train.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainCreatedOutboxEventHandlerHelper implements OutboxEventHandlerHelper {

    private final TrainRepository trainRepository;

    @Override
    public boolean support(OutboxEvent outboxEvent) {
        return outboxEvent.getAggregateType().equals(AggregateType.DATAFABRIC_TRAIN)
            && outboxEvent.getEventType().equals(TrainCreatedEvent.class.getName());
    }

    @Override
    public void handle(Event domainEvent) {

        TrainCreatedEvent trainCreatedEvent = (TrainCreatedEvent) domainEvent;
        if (!trainRepository.findById(trainCreatedEvent.getTrainId()).isPresent()) {
            return;
        }

        // TODO: Train Workflow 생성하는 로직
    }
}
