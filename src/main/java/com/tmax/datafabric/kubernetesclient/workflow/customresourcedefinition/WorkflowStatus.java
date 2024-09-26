package com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkflowStatus implements KubernetesResource {

    private Instant startedAt;
    private List<Condition> conditions;
    private Instant finishedAt;
    private String message;
    private String phase;
    private Object artifactGCStatus;
    private Object artifactRepositoryRef;
    private Map<String, NodeStatus> nodes;
    private String progress;
    private Map<String, Long> resourcesDuration = new HashMap<String, Long>();
}
