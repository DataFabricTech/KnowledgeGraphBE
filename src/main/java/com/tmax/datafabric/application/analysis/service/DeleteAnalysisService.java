package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.DeleteAnalysisUseCase;
import com.tmax.datafabric.application.exception.InvalidAnalysisIdException;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import com.tmax.datafabric.domain.event.AnalysisDeletedEvent;
import com.tmax.datafabric.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteAnalysisService implements DeleteAnalysisUseCase {
    private final AnalysisRepository analysisRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    @Override
    public void delete(Long analysisId) {
        Analysis analysis = analysisRepository.findById(analysisId).orElseThrow(
            InvalidAnalysisIdException::new);

        eventPublisher.publish(AnalysisDeletedEvent.from(analysis));
        analysisRepository.delete(analysisId);
    }
}
