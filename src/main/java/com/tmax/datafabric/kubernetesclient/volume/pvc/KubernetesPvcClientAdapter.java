package com.tmax.datafabric.kubernetesclient.volume.pvc;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimSpec;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimSpecBuilder;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.client.KubernetesClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KubernetesPvcClientAdapter implements KubernetesPvcClient {
    private final KubernetesClient kubernetesClient;

    @Override
    public PersistentVolumeClaim createOrReplacePvc(String namespace, String pvcName,
        ResourceRequirements resourceRequirements, List<String> accessModes) {
        ObjectMeta objectMeta = new ObjectMetaBuilder().withNamespace(namespace).withName(pvcName)
            .build();

        PersistentVolumeClaimSpec pvcSpec = new PersistentVolumeClaimSpecBuilder()
            .withResources(resourceRequirements).withAccessModes(accessModes).build();

        PersistentVolumeClaim persistentVolumeClaim = new PersistentVolumeClaimBuilder()
            .withMetadata(objectMeta)
            .withSpec(pvcSpec).build();

        return kubernetesClient.resources(PersistentVolumeClaim.class)
            .resource(persistentVolumeClaim).create();
    }

    @Override
    public void deletePvc(String namespace, String pvcName) {
        kubernetesClient.resources(PersistentVolumeClaim.class).inNamespace(namespace)
            .withName(pvcName).delete();
    }

    @Override
    public PersistentVolumeClaim getPvc(String namespace, String pvcName) {
        return kubernetesClient.resources(PersistentVolumeClaim.class).inNamespace(namespace)
            .withName(pvcName).get();
    }
}
