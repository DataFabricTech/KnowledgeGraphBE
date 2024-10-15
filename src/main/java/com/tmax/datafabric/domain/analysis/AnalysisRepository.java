package com.tmax.datafabric.domain.analysis;

import java.util.Optional;

public interface AnalysisRepository {

    Analysis save(Analysis analysis);

    Optional<Analysis> findById(Long id);
}
