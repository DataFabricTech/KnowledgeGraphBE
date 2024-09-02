package com.tmax.datafabric.application.workflow.port.dto;

import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.openshift.api.model.Parameter;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Argument implements KubernetesResource {

    private List<Parameter> parameters = new ArrayList<>();

    private Argument(List<Parameter> parameters) {
        this.parameters.addAll(parameters);
    }

    public static Argument createArgument(List<Parameter> parameters) {
        return new Argument(parameters);
    }
}
