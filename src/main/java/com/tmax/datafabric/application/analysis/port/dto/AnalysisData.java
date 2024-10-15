package com.tmax.datafabric.application.analysis.port.dto;

import static java.util.stream.Collectors.toList;

import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisData {
    private Long id;

    private String name;

    private String creator;

    private AnalysisStatus analysisStatus;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;


    public static AnalysisData from(Analysis analysis) {
        return new AnalysisData(analysis.getId(), analysis.getName(), analysis.getCreator(),
            analysis.getStatus(), analysis.getCreatedAt(), analysis.getFinishedAt());
    }
}
