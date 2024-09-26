package com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.kubernetes.api.model.Volume;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Template implements KubernetesResource {

    private String name;
    private Container container;
    private List<Volume> volumes;
    private Input input;
    private DagTemplate dag;

    private Template(String name, Container container, Input input, DagTemplate dag) {
        this.name = name;
        this.container = container != null ? container : null;
        this.input = input != null ? input : null;
        this.dag = dag != null ? dag : null;
    }

    public static Template createTemplate(String name, Container container, Input input, DagTemplate dag) {
        return new Template(name, container, input, dag);
    }

    public static Template createTemplate(String name, Input input, DagTemplate dag) {
        return new Template(name, null, input, dag);
    }

    public static Template createTemplate(String name, Container container, DagTemplate dag) {
        return new Template(name, container, null, dag);
    }

    public static Template createTemplate(String name, Container container, Input input) {
        return new Template(name, container, input, null);
    }

    public static Template createTemplate(String name, DagTemplate dag) {
        return new Template(name, null, null, dag);
    }

    public static Template createTemplate(String name, Container container) {
        return new Template(name, container, null, null);
    }

    public static Template createTemplate(String name, Input input) {
        return new Template(name, null, input, null);
    }

    public static Template createTemplate(String name) {
        return new Template(name, null, null, null);
    }
}
