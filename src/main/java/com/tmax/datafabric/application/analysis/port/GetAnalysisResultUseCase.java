package com.tmax.datafabric.application.analysis.port;

import com.tmax.datafabric.application.analysis.port.dto.DataRelationData;

public interface GetAnalysisResultUseCase {
    DataRelationData getRelation(Long analysisId, String dataId, String modelType);
}
