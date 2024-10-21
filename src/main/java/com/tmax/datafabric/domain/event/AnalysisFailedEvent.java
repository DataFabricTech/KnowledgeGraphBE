package com.tmax.datafabric.domain.event;

import com.tmax.datafabric.domain.analysis.Analysis;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisFailedEvent extends Event {
    private Long analysisId;

    @Builder
    private AnalysisFailedEvent(Long analysisId) {
        this.analysisId = analysisId;
    }

    public static AnalysisFailedEvent from(Analysis analysis) {
        return AnalysisFailedEvent.builder().analysisId(analysis.getId()).build();
    }
}
