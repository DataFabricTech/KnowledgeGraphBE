package com.tmax.datafabric.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisCreatedEvent extends Event {

    private Long analysisId;
    private String inputDataPath;
    private String solutionType;
    private String imageName;
    private String modelHyperparameters;
    private String featureHyperparameters;
    private String learningHyperparameters;
    private String cpuSize;
    private String memorySize;

    protected AnalysisCreatedEvent(Long analysisId, String inputDataPath, String solutionType,
        String imageName, String modelHyperparameters, String featureHyperparameters,
        String learningHyperparameters, String cpuSize, String memorySize) {
        this.analysisId = analysisId;
        this.inputDataPath = inputDataPath;
        this.solutionType = solutionType;
        this.imageName = imageName;
        this.modelHyperparameters = modelHyperparameters;
        this.featureHyperparameters = featureHyperparameters;
        this.learningHyperparameters = learningHyperparameters;
        this.cpuSize = cpuSize;
        this.memorySize = memorySize;
    }

    public static AnalysisCreatedEvent create(Long analysisId, String inputDataPath,
        String solutionType,  String imageName, String modelHyperparameters,
        String featureHyperparameters, String learningHyperparameters, String cpuSize,
        String memorySize) {
        return new AnalysisCreatedEvent(analysisId, inputDataPath, solutionType, imageName,
            modelHyperparameters, featureHyperparameters, learningHyperparameters, cpuSize,
            memorySize);
    }
}
