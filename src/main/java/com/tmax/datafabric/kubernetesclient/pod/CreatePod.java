package com.tmax.datafabric.kubernetesclient.pod;

import io.fabric8.kubernetes.api.model.Pod;

public interface CreatePod {
    Pod createOrReplacePod(Pod pod);
}
