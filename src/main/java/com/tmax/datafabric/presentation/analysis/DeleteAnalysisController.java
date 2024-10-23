package com.tmax.datafabric.presentation.analysis;

import com.tmax.datafabric.application.analysis.port.DeleteAnalysisUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestAnalysisController
@RequiredArgsConstructor
public class DeleteAnalysisController {
    private final DeleteAnalysisUseCase deleteAnalysisUseCase;

    @DeleteMapping("/{analysisId}")
    public void deleteAnalysis(@PathVariable Long analysisId) {
        deleteAnalysisUseCase.delete(analysisId);
    }
}
