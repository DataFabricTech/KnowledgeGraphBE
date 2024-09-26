package com.tmax.datafabric.kubernetesclient.pod;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KubernetesPodClientAdapter implements KubernetesPodClient {
    private final KubernetesClient kubernetesClient;

    @Override
    public Pod createOrReplacePod(Pod pod) {
        return kubernetesClient.resources(Pod.class).resource(pod).createOrReplace();
    }

    @Override
    public void deletePod(String namespace, String podName) {
        kubernetesClient.resources(Pod.class).inNamespace(namespace).withName(podName).delete();
    }

    @Override
    public void execPodAsync(String namespace, String podName, String[] commands) {
        ExecWatch watch = kubernetesClient.pods().inNamespace(namespace)
            .withName(podName).inContainer(podName).redirectingInput().exec(commands);
        watch.close();
    }

    @Override
    public Pod getPod(String namespace, String podName) {
        return kubernetesClient.resources(Pod.class).inNamespace(namespace).withName(podName).get();
    }
}
