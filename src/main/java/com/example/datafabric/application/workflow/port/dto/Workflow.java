package com.example.datafabric.application.workflow.port.dto;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Version(Workflow.VERSION)
@Group(Workflow.GROUP)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Workflow extends CustomResource<WorkflowSpec, WorkflowStatus> implements Namespaced {

    public static final String GROUP = "argoproj.io";
    public static final String VERSION = "v1alpha1";

    public static Workflow createWorkflow(ObjectMeta objectMeta, WorkflowSpec workflowSpec) {
        Workflow workflow = new Workflow();
        workflow.setMetadata(objectMeta);
        workflow.setSpec(workflowSpec);
        return workflow;
    }

    public static Workflow createWorkflow(ObjectMeta objectMeta,
        WorkflowSpec workflowSpec,
        WorkflowStatus workflowStatus) {
        Workflow workflow = new Workflow();
        workflow.setMetadata(objectMeta);
        workflow.setSpec(workflowSpec);
        workflow.setStatus(workflowStatus);
        return workflow;
    }
}
