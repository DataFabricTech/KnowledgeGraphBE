package com.tmax.datafabric.application.train.port.dto;

import com.tmax.datafabric.domain.train.HyperParameter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTrainCommand {

    private String name;
    private String inputDataPath;
    private String solutionType;
    private String modelType;
    private HyperParameterDto hyperparameterDto;


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HyperParameterDto {

        private String modelHyperparameters;
        private String featureHyperparameters;
        private String learningHyperparameters;

        public static HyperParameter to(HyperParameterDto hyperparameterDto) {
            return new HyperParameter(hyperparameterDto.getModelHyperparameters(),
                hyperparameterDto.getFeatureHyperparameters(),
                hyperparameterDto.getLearningHyperparameters());
        }

        public static HyperParameterDto from(HyperParameter hyperparameter) {
            return new HyperParameterDto(hyperparameter.getModelHyperparameters(),
                hyperparameter.getFeatureHyperparameters(), hyperparameter.getLearningHyperparameters());
        }
    }
}

