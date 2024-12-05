package com.tmax.datafabric.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisCreatedEvent extends Event {

    private Long analysisId;
    private String dataSourceType;
    private String inputDataPath;
    private String solutionType;
    private String imageName;
    private String cpuSize;
    private String memorySize;

    protected AnalysisCreatedEvent(Long analysisId, String datasourceType, String inputDataPath, String solutionType,
                                  String imageName, String cpuSize, String memorySize) {
        this.analysisId = analysisId;
        this.dataSourceType = datasourceType;
        this.inputDataPath = inputDataPath;
        this.solutionType = solutionType;
        this.imageName = imageName;
        this.cpuSize = cpuSize;
        this.memorySize = memorySize;
    }

    public static AnalysisCreatedEvent create(Long analysisId, String datasourceType, String inputDataPath,
        String solutionType,  String imageName, String cpuSize, String memorySize) {
        return new AnalysisCreatedEvent(analysisId, datasourceType, inputDataPath, solutionType, imageName, cpuSize,
            memorySize);
    }
}
