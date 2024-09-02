package com.tmax.datafabric.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainCreatedEvent extends Event {

    private Long trainId;
    private String dataPath;
    private String model;
    private String modelHyperparameters;
    private String featureHyperparameters;
    private String learningHyperparameters;

    protected TrainCreatedEvent(Long trainId, String dataPath, String model,
        String modelHyperparameters, String featureHyperparameters,
        String learningHyperparameters) {
        this.trainId = trainId;
        this.dataPath = dataPath;
        this.model = model != null ? model : "exampleModel";
        this.modelHyperparameters = modelHyperparameters != null ? modelHyperparameters : null;
        this.featureHyperparameters =
            featureHyperparameters != null ? featureHyperparameters : null;
        this.learningHyperparameters =
            learningHyperparameters != null ? learningHyperparameters : null;
    }


    public static TrainCreatedEvent create(Long trainId, String dataPath, String model,
        String modelHyperparameters, String featureHyperparameters,
        String learningHyperparameters) {
        return new TrainCreatedEvent(trainId, dataPath, model, modelHyperparameters,
            featureHyperparameters, learningHyperparameters);
    }
}
