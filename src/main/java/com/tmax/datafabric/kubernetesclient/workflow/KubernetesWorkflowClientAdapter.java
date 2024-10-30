package com.tmax.datafabric.kubernetesclient.workflow;

import com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition.Workflow;
import io.fabric8.kubernetes.client.KubernetesClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KubernetesWorkflowClientAdapter implements KubernetesWorkflowClient {
    private final KubernetesClient kubernetesClient;

    @Override
    public Workflow createWorkflow(Workflow workflow) {
        return kubernetesClient.resources(Workflow.class).resource(workflow).create();
    }

    @Override
    public void deleteWorkflow(String namespace, String workflowName) {
        kubernetesClient.resources(Workflow.class).inNamespace(namespace).withName(workflowName)
            .delete();
    }

    @Override
    public void deleteWorkflow(String namespace, Map<String, String> labels) {
        kubernetesClient.resources(Workflow.class).inNamespace(namespace).withLabels(labels)
            .delete();
    }

    @Override
    public Workflow getWorkflow(String namespace, String workflowName) {
        return kubernetesClient.resources(Workflow.class).inNamespace(namespace)
            .withName(workflowName).get();
    }
}
