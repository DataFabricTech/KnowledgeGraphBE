package com.tmax.datafabric.presentation.analysis;

import com.tmax.datafabric.application.analysis.port.GetAnalysisResultUseCase;
import com.tmax.datafabric.application.analysis.port.GetAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import com.tmax.datafabric.application.analysis.port.dto.DataRelationData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestAnalysisController
@RequiredArgsConstructor
public class GetAnalysisController {
    private final GetAnalysisUseCase getAnalysisUseCase;
    private final GetAnalysisResultUseCase getAnalysisResultUseCase;

    @GetMapping("/{analysisId}")
    public AnalysisData getAnalysis(@PathVariable Long analysisId) {
        return getAnalysisUseCase.getAnalysis(analysisId);
    }

    @GetMapping("/all")
    public List<AnalysisData> getAllAnalysis() {
        return getAnalysisUseCase.getAllAnalysis();
    }

    @GetMapping("/{analysisId}/relation")
    public DataRelationData getRelationViaAssociationAnalysis(@PathVariable Long analysisId,
        @RequestParam(name = "data-id") String dataId) {
        String modelType = "association";
        return getAnalysisResultUseCase.getRelation(analysisId, dataId, modelType);
    }
}
