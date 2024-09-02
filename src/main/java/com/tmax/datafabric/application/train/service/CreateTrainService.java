package com.tmax.datafabric.application.train.service;

import com.tmax.datafabric.application.train.port.CreateTrainUseCase;
import com.tmax.datafabric.application.train.port.dto.CreateTrainCommand;
import com.tmax.datafabric.domain.event.EventPublisher;
import com.tmax.datafabric.domain.event.TrainCreatedEvent;
import com.tmax.datafabric.domain.train.Train;
import com.tmax.datafabric.domain.train.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTrainService implements CreateTrainUseCase {

    private final TrainRepository trainRepository;
    private final EventPublisher eventPublisher;

    @Override
    public void create(CreateTrainCommand createTrainCommand) {
        //1. DB에 저장
        Train train = trainRepository.save(Train.create());

        //2. TrainCreatedEvent 발행
        eventPublisher.publish(TrainCreatedEvent.create(train.getTrainId(),
            createTrainCommand.getDataPath(),
            createTrainCommand.getModel(),
            createTrainCommand.getHyperparameter().getModelHyperparameters(),
            createTrainCommand.getHyperparameter().getFeatureHyperparameters(),
            createTrainCommand.getHyperparameter().getLearningHyperparameters()));
    }
}
