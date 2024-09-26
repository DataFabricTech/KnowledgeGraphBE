package com.tmax.datafabric.kubernetesclient.workflow;

import java.util.Map;

public interface DeleteWorkflow {
    void deleteWorkflow(String namespace, String workflowName);

    void deleteWorkflow(String namespace, Map<String, String> labels);
}
