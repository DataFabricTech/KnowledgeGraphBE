package com.example.datafabric.application.train.port.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTrainCommand {

    private String dataPath;
    private String model;
    private HyperparameterDto hyperparameter;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HyperparameterDto {

        private String modelHyperparameters;
        private String featureHyperparameters;
        private String learningHyperparameters;
    }
}

