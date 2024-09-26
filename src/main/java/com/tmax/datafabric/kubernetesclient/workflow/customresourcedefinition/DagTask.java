package com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition;

import io.fabric8.kubernetes.api.model.KubernetesResource;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DagTask implements KubernetesResource {

    private String template;
    private Argument argument;
    private String name;
    private List<String> dependencies = new ArrayList<>();

    private DagTask(String name, List<String> dependencies, String template, Argument argument) {
        this.name = name;
        if (dependencies != null) {
            this.dependencies.addAll(dependencies);
        }
        this.template = template;
        this.argument = argument != null ? argument : null;
    }

    public static DagTask createDagTask(String name, List<String> dependencies, String template, Argument argument) {
        return new DagTask(name, dependencies, template, argument);
    }

    public static DagTask createDagTask(String name, String template, Argument argument) {
        return new DagTask(name, null, template, argument);
    }

    public static DagTask createDagTask(String name, List<String> dependencies, String template) {
        return new DagTask(name, dependencies, template, null);
    }

    public static DagTask createDagTask(String name, String template) {
        return new DagTask(name, null, template, null);
    }

}
