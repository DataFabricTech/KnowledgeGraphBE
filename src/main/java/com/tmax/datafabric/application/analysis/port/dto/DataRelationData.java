package com.tmax.datafabric.application.analysis.port.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataRelationData {
    private List<DataRelationDto> dataRelationList;

    public static DataRelationData from(List<DataRelationDto> dataRelationList) {
        return new DataRelationData(dataRelationList);
    }
}
