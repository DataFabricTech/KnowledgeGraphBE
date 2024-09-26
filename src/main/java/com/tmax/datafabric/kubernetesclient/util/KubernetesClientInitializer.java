package com.tmax.datafabric.kubernetesclient.util;

import com.tmax.datafabric.application.config.DatafabricConst;
import com.tmax.datafabric.application.config.ImageConfig;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.namespace.KubernetesNamespaceClient;
import com.tmax.datafabric.kubernetesclient.pod.KubernetesPodClient;
import com.tmax.datafabric.kubernetesclient.rbac.secret.KubernetesSecretClient;
import com.tmax.datafabric.kubernetesclient.rbac.serviceaccount.KubernetesServiceAccountClient;
import com.tmax.datafabric.kubernetesclient.volume.pvc.KubernetesPvcClient;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimVolumeSource;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import io.fabric8.kubernetes.api.model.ResourceRequirementsBuilder;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMount;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KubernetesClientInitializer {

    private final KubernetesConfig kubernetesConfig;
    private final KubernetesNamespaceClient namespaceClient;
    private final KubernetesPodClient podClient;
    private final KubernetesPvcClient pvcClient;
    private final KubernetesSecretClient secretClient;
    private final KubernetesServiceAccountClient serviceAccountClient;
    private final ImageConfig imageConfig;

    @PostConstruct
    private void createOrReplaceInitialResource() {
        final String nsName = kubernetesConfig.getNamespace();
        final String pvcName = kubernetesConfig.getPvcName();
        final String podName = kubernetesConfig.getPodName();
        final String secretName = kubernetesConfig.getImagePullSecret();

        // Create Default Namespace
        if (Optional.ofNullable(namespaceClient.getNamespace(nsName)).isEmpty()) {
            createNamespace(namespaceClient, nsName);
        }

        // Create Default PersistentVolumeClaim
        if (Optional.ofNullable(pvcClient.getPvc(nsName, pvcName)).isEmpty()) {
            createPvc(pvcClient, nsName, pvcName);
        }

        // Create Default Pod
        if (Optional.ofNullable(podClient.getPod(nsName, podName)).isEmpty()) {
            createPod(podClient, nsName, pvcName, podName);
        }

    }

    private void createNamespace(KubernetesNamespaceClient namespaceClient, String name) {
        namespaceClient.createOrReplaceNamespace(name);
    }

    private void createPvc(KubernetesPvcClient pvcClient, String nsName, String pvcName) {
        Map<String, Quantity> requests = new HashMap<>();
        requests.put("storage", new Quantity("3Gi"));
        ResourceRequirements resourceRequirements = new ResourceRequirementsBuilder()
            .withRequests(requests).build();
        pvcClient.createOrReplacePvc(nsName, pvcName, resourceRequirements,
            new ArrayList<>(Arrays.asList("ReadWriteMany")));
    }

    private void createPod(KubernetesPodClient podClient, String namespaceName, String pvcName,
        String podName) {
        PersistentVolumeClaimVolumeSource persistentVolumeClaim
            = new PersistentVolumeClaimVolumeSourceBuilder().withClaimName(pvcName).build();

        Volume volume = new VolumeBuilder()
            .withName(pvcName).withPersistentVolumeClaim(persistentVolumeClaim).build();

        VolumeMount volumeMount = new VolumeMountBuilder()
            .withMountPath(DatafabricConst.DATAFABRIC_MOUNT_PATH).withName(pvcName).build();

        Container container = new ContainerBuilder()
            .withName(podName).withImage(imageConfig.getDownloaderImageName()).withVolumeMounts(volumeMount)
            .build();

        Pod pod = new PodBuilder().withNewMetadata().withNamespace(namespaceName).withName(podName)
            .endMetadata().withNewSpec().withContainers(container).withVolumes(volume).endSpec()
            .build();

        podClient.createOrReplacePod(pod);
    }
}
