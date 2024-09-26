package com.tmax.datafabric.kubernetesclient.workflow;

import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Workflow;

public interface GetWorkflow {
    Workflow getWorkflow(String namespace, String workflowName);
}
