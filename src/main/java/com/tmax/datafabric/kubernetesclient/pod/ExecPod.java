package com.tmax.datafabric.kubernetesclient.pod;

public interface ExecPod {
    void execPodAsync(String namespace, String podName, String[] commands);
}
