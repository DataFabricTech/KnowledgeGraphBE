package com.tmax.datafabric.infrastructure.analysis;

import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalysisRepositoryAdapter implements AnalysisRepository {

    private final JpaAnalysisRepository jpaAnalysisRepository;

    @Override
    public Analysis save(Analysis analysis) {
        return jpaAnalysisRepository.save(analysis);
    }

    @Override
    public Optional<Analysis> findById(Long id) {
        return jpaAnalysisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        jpaAnalysisRepository.deleteById(id);
    }
}
