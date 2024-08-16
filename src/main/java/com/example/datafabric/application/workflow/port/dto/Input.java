package com.example.datafabric.application.workflow.port.dto;

import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.openshift.api.model.Parameter;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Input implements KubernetesResource {

    private List<Parameter> parameters;

    private Input(List<Parameter> parameters) {
        this.parameters.addAll(parameters);
    }

    public static Input createInput(List<Parameter> parameters) {
        return new Input(parameters);
    }

}
