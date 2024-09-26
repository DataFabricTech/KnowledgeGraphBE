package com.tmax.datafabric.domain.train;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class HyperParameter {
    private String modelHyperparameters;
    private String featureHyperparameters;
    private String learningHyperparameters;
}
