package com.tmax.datafabric.domain.analysis;

import java.util.List;
import java.util.Optional;

public interface AnalysisRepository {

    Analysis save(Analysis analysis);

    Optional<Analysis> findById(Long id);

    List<Analysis> findAll();

    void delete(Long id);
}
