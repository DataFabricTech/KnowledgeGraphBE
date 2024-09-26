package com.tmax.datafabric.kubernetesclient.rbac.secret;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KubernetesSecretClientAdapter implements KubernetesSecretClient {
    private final KubernetesClient kubernetesClient;

    @Override
    public Secret createOrReplaceSecret(String namespace, String secretName, String type) {
        ObjectMeta objectMeta = new ObjectMetaBuilder().withNamespace(namespace)
            .withName(secretName).build();

        Secret secret = new SecretBuilder().withMetadata(objectMeta).withType(type).build();

        return kubernetesClient.resources(Secret.class).inNamespace(namespace).resource(secret)
            .createOrReplace();
    }
}
