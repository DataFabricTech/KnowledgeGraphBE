package com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.Volume;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkflowSpec implements KubernetesResource {

    private List<Volume> volumes = new ArrayList<>();
    private String serviceAccountName;
    private String entrypoint;
    private List<Template> templates = new ArrayList<>();
    private List<LocalObjectReference> imagePullSecrets = new ArrayList<>();
    private Argument arguments;

    private WorkflowSpec(String entryPoint, List<Volume> volumes, String serviceAccountName, Argument arguments,
        List<Template> templates,
        List<LocalObjectReference> imagePullSecrets) {
        this.entrypoint = entryPoint;
        this.serviceAccountName = serviceAccountName;
        this.arguments = arguments;
        if (volumes != null) {
            this.volumes.addAll(volumes);
        }
        if (templates != null) {
            this.templates.addAll(templates);
        }
        if (imagePullSecrets != null) {
            this.imagePullSecrets.addAll(imagePullSecrets);
        }

    }

    public static WorkflowSpec createWorkflowSpec(String entryPoint, List<Volume> volumes, String serviceAccountName,
        List<Template> templates, List<String> imagePullSecrets) {
        List<LocalObjectReference> imagePullSecretsWrapper = new ArrayList<>();
        for (String imagePullSecret : imagePullSecrets) {
            imagePullSecretsWrapper.add(new LocalObjectReference(imagePullSecret));
        }
        return new WorkflowSpec(entryPoint, volumes, serviceAccountName, null, templates, imagePullSecretsWrapper);
    }

    public static WorkflowSpec createWorkflowSpec(String entryPoint, List<Volume> volumes, String serviceAccountName,
        List<Template> templates) {
        return new WorkflowSpec(entryPoint, volumes, serviceAccountName, null, templates, null);
    }

    public static WorkflowSpec createWorkflowSpec(String entryPoint, List<Volume> volumes, String serviceAccountName,
        Argument arguments, List<Template> templates, List<String> imagePullSecrets) {
        List<LocalObjectReference> imagePullSecretsWrapper = new ArrayList<>();
        for (String imagePullSecret : imagePullSecrets) {
            imagePullSecretsWrapper.add(new LocalObjectReference(imagePullSecret));
        }
        return new WorkflowSpec(entryPoint, volumes, serviceAccountName, arguments, templates, imagePullSecretsWrapper);
    }

    public static WorkflowSpec createWorkflowSpec(String entryPoint, List<Volume> volumes, String serviceAccountName,
        Argument arguments, List<Template> templates) {
        return new WorkflowSpec(entryPoint, volumes, serviceAccountName, arguments, templates, null);
    }
}



