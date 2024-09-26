package com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Condition {

    private String message;
    private String status;
    private String type;
}
