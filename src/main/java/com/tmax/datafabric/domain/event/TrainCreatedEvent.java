package com.tmax.datafabric.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainCreatedEvent extends Event {

    private Long trainId;
    private String inputDataPath;
    private String solutionType;
    private String modelType;
    private String modelHyperparameters;
    private String featureHyperparameters;
    private String learningHyperparameters;

    protected TrainCreatedEvent(Long trainId, String inputDataPath, String solutionType,
        String modelType, String modelHyperparameters, String featureHyperparameters,
        String learningHyperparameters) {
        this.trainId = trainId;
        this.inputDataPath = inputDataPath;
        this.solutionType = solutionType;
        this.modelType = modelType != null ? modelType : "exampleModel";
        this.modelHyperparameters = modelHyperparameters;
        this.featureHyperparameters = featureHyperparameters;
        this.learningHyperparameters = learningHyperparameters;
    }

    public static TrainCreatedEvent create(Long trainId, String inputDataPath, String solutionType,
        String modelType, String modelHyperparameters, String featureHyperparameters,
        String learningHyperparameters) {
        return new TrainCreatedEvent(trainId, inputDataPath, solutionType, modelType,
            modelHyperparameters, featureHyperparameters, learningHyperparameters);
    }
}
