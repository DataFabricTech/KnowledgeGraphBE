package com.tmax.datafabric.kubernetesclient.rbac.serviceaccount;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KubernetesServiceAccountClientAdapter implements KubernetesServiceAccountClient {
    private final KubernetesClient kubernetesClient;

    @Override
    public ServiceAccount createOrReplaceServiceAccount(String namespace, String serviceAccountName,
        String secretName) {
        ObjectMeta objectMeta = new ObjectMetaBuilder().withNamespace(namespace)
            .withName(serviceAccountName).build();

        ServiceAccount serviceAccount = new ServiceAccountBuilder().withMetadata(objectMeta)
            .addNewSecret().withName(secretName).endSecret().build();

        return kubernetesClient.resources(ServiceAccount.class).inNamespace(namespace).
        resource(serviceAccount).createOrReplace();
    }

    @Override
    public ServiceAccount getServiceAccount(String namespace, String serviceAccountName) {
        return kubernetesClient.resources(ServiceAccount.class).inNamespace(namespace)
            .withName(serviceAccountName).get();
    }
}
