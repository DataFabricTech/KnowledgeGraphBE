package com.tmax.datafabric.kubernetesclient.workflow;

import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Workflow;

public interface CreateWorkflow {
    Workflow createOrReplaceWorkflow(Workflow workflow);
}
