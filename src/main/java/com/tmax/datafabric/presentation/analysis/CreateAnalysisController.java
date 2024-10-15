package com.tmax.datafabric.presentation.analysis;

import com.tmax.datafabric.application.analysis.port.CreateAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import com.tmax.datafabric.application.analysis.port.dto.CreateAnalysisCommand;
import com.tmax.datafabric.domain.analysis.AnalysisSolutionType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestAnalysisController
@RequiredArgsConstructor
public class CreateAnalysisController {
    private final CreateAnalysisUseCase createAnalysisUseCase;

    // TODO: input command(AnalysisCommand), return type (AnalysisData) 만들기
    @PostMapping("")
    public AnalysisData createAssociationAnalysis(@RequestBody CreateAnalysisCommand command) {
        return createAnalysisUseCase.create(command);
    }
}
