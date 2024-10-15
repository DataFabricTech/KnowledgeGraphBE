package com.tmax.datafabric.infrastructure.train;

import com.tmax.datafabric.domain.train.Analysis;
import com.tmax.datafabric.domain.train.AnalysisRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalysisRepositoryAdapter implements AnalysisRepository {

    private final JpaTrainRepository jpaTrainRepository;

    @Override
    public Analysis save(Analysis analysis) {
        return jpaTrainRepository.save(analysis);
    }

    @Override
    public Optional<Analysis> findById(Long id) {
        return jpaTrainRepository.findById(id);
    }
}
