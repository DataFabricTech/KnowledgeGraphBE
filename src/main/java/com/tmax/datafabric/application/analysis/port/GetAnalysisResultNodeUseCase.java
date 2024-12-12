package com.tmax.datafabric.application.analysis.port;

import com.tmax.datafabric.domain.RelationResult;

public interface GetAnalysisResultNodeUseCase {
    RelationResult getRelationResultNodes(Long analysisId, String dataId, String modelType);
}
