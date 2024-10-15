package com.tmax.datafabric.application.analysis.port;

import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand;

public interface CreateAnalysisUseCase {

    AnalysisData create(CreateAnalysisCommand createAnalysisCommand);
}
