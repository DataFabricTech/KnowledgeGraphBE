package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.FailAnalysisUseCase;
import com.tmax.datafabric.application.exception.InvalidAnalysisIdException;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailAnalysisService implements FailAnalysisUseCase {
    private final AnalysisRepository analysisRepository;

    @Override
    public void fail(Long analysisId) {
        Analysis analysis = analysisRepository.findById(analysisId).orElseThrow(
            InvalidAnalysisIdException::new);

        analysis.fail();
    }
}
