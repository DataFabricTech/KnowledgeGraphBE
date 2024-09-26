package com.tmax.datafabric.kubernetesclient.pod;

import io.fabric8.kubernetes.api.model.Pod;

public interface GetPod {
    Pod getPod(String namespace, String podName);
}
