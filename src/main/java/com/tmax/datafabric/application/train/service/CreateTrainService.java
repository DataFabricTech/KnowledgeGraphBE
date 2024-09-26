package com.tmax.datafabric.application.train.service;

import com.tmax.datafabric.application.train.port.CreateTrainUseCase;
import com.tmax.datafabric.application.train.port.dto.CreateTrainCommand;
import com.tmax.datafabric.application.train.port.dto.CreateTrainCommand.HyperParameterDto;
import com.tmax.datafabric.domain.event.EventPublisher;
import com.tmax.datafabric.domain.event.TrainCreatedEvent;
import com.tmax.datafabric.domain.train.HyperParameter;
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
    public void create(CreateTrainCommand command) {
        //1. DB에 저장
        HyperParameter hyperParameter = HyperParameterDto.to(command.getHyperparameterDto());
        Train train = Train.createTrain(command.getName(), command.getInputDataPath(),
            command.getSolutionType(), command.getModelType(), hyperParameter);

        Train savedTrain = trainRepository.save(train);

        //2. TrainCreatedEvent 발행
        eventPublisher.publish(TrainCreatedEvent.create(savedTrain.getTrainId(),
            savedTrain.getInputDataPath(), savedTrain.getSolutionType(), savedTrain.getModelType(),
            hyperParameter.getModelHyperparameters(), hyperParameter.getFeatureHyperparameters(),
            hyperParameter.getLearningHyperparameters()));
    }
}
