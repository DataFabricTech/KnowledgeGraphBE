package com.tmax.datafabric.kubernetesclient.workflow.customresourcedefinition;

import io.fabric8.kubernetes.api.model.KubernetesResource;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DagTemplate implements KubernetesResource {

    private List<DagTask> tasks = new ArrayList<>();

    private DagTemplate(List<DagTask> tasks) {
        this.tasks.addAll(tasks);
    }

    public static DagTemplate createDagTemplate(List<DagTask> tasks) {
        return new DagTemplate(tasks);
    }

}
