package com.tmax.datafabric.application.analysis.port.dto;

import com.tmax.datafabric.domain.analysis.HyperParameter;
import com.tmax.datafabric.domain.analysis.ResourceSpec;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CreateAnalysisCommand {
    private String name;
    private String inputDataPath;
    private String solutionType;
    private String creator;
    private HyperparameterDto hyperparameterDto;
    private ResourceSpecDto resourceSpecDto;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HyperparameterDto {

        private String modelHyperparameters;
        private String featureHyperparameters;
        private String learningHyperparameters;

        public static HyperParameter to(HyperparameterDto hyperparameterDto) {
            return new HyperParameter(hyperparameterDto.getModelHyperparameters(),
                hyperparameterDto.getFeatureHyperparameters(),
                hyperparameterDto.getLearningHyperparameters());
        }

        public static HyperparameterDto from(HyperParameter hyperparameter) {
            return new HyperparameterDto(hyperparameter.getModelHyperparameters(),
                hyperparameter.getFeatureHyperparameters(), hyperparameter.getLearningHyperparameters());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ResourceSpecDto {
        private String cpuSize;
        private String memorySize;

        public static ResourceSpec to(ResourceSpecDto resourceSpecDto) {
            return new ResourceSpec(resourceSpecDto.getCpuSize(), resourceSpecDto.getMemorySize());
        }

        public static ResourceSpecDto from(ResourceSpec resourceSpec) {
            return new ResourceSpecDto(resourceSpec.getCpuSize(), resourceSpec.getMemorySize());
        }
    }
}

