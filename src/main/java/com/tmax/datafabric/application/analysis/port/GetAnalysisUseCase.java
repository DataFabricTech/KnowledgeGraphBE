package com.tmax.datafabric.application.analysis.port;

import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;

public interface GetAnalysisUseCase {
    AnalysisData getAnalysis(Long analysisId);
}
