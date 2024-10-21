package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.GetAnalysisUseCase;
import com.tmax.datafabric.application.analysis.port.dto.AnalysisData;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAnalysisService implements GetAnalysisUseCase {
    private final AnalysisRepository analysisRepository;

    @Override
    public AnalysisData getAnalysis(Long analysisId) {

        Analysis analysis = analysisRepository.findById(analysisId).orElseThrow();

        return AnalysisData.from(analysis);
    }
}
