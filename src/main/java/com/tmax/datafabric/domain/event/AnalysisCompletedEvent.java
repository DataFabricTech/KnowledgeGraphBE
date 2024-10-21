package com.tmax.datafabric.domain.event;

import com.tmax.datafabric.domain.analysis.Analysis;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisCompletedEvent extends Event {
    private Long analysisId;

    @Builder
    private AnalysisCompletedEvent(Long analysisId) {
        this.analysisId = analysisId;
    }

    public static AnalysisCompletedEvent from(Analysis analysis) {
        return AnalysisCompletedEvent.builder().analysisId(analysis.getId()).build();
    }
}
