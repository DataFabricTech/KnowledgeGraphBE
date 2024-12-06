package com.tmax.datafabric.application.analysis.port.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataRelationDto {
    private String dataId;
    private Double score;

    @Builder
    public DataRelationDto(String dataId, Double score) {
        this.dataId = dataId;
        this.score = score;
    }
}
