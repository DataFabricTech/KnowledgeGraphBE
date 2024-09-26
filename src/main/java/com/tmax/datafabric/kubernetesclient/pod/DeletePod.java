package com.tmax.datafabric.kubernetesclient.pod;

public interface DeletePod {
    void deletePod(String namespace, String podName);
}
