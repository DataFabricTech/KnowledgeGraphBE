package com.tmax.datafabric.domain.event;

import com.tmax.datafabric.domain.analysis.Analysis;
import lombok.Builder;

public class AnalysisDeletedEvent extends Event {
    private Long analysisId;

    @Builder
    private AnalysisDeletedEvent(Long analysisId) {
        this.analysisId = analysisId;
    }

    public static AnalysisDeletedEvent from(Analysis analysis) {
        return AnalysisDeletedEvent.builder().analysisId(analysis.getId()).build();
    }
}
