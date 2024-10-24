package com.tmax.datafabric.application.analysis.port;

import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import java.util.List;

public interface GetAnalysisUseCase {
    AnalysisData getAnalysis(Long analysisId);

    List<AnalysisData> getAllAnalysis();
}
