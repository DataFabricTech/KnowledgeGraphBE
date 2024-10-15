package com.tmax.datafabric.infrastructure.analysis;

import com.tmax.datafabric.domain.analysis.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAnalysisRepository extends JpaRepository<Analysis, Long> {

}
