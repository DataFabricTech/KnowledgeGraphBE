package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.RunningAnalysisUseCase;
import com.tmax.datafabric.application.exception.InvalidAnalysisIdException;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunningAnalysisService implements RunningAnalysisUseCase {
    private final AnalysisRepository analysisRepository;

    @Transactional
    @Override
    public void running(Long analysisId) {
        Analysis analysis = analysisRepository.findById(analysisId).orElseThrow(
            InvalidAnalysisIdException::new);

        analysis.running();
    }
}
