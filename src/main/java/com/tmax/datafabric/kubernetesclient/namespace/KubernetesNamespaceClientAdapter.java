package com.tmax.datafabric.kubernetesclient.namespace;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KubernetesNamespaceClientAdapter implements KubernetesNamespaceClient {
    private final KubernetesClient kubernetesClient;

    @Override
    public Namespace createOrReplaceNamespace(String name) {
        ObjectMeta objectMeta = new ObjectMetaBuilder().withName(name).build();
        Namespace namespace = new NamespaceBuilder().withMetadata(objectMeta).build();

        return kubernetesClient.resources(Namespace.class).resource(namespace).createOrReplace();
    }

    @Override
    public Namespace createOrReplaceNamespace(Namespace namespace) {
        return kubernetesClient.resources(Namespace.class).resource(namespace).createOrReplace();
    }

    @Override
    public void deleteNamespace(String name) {
        kubernetesClient.resources(Namespace.class).withName(name).delete();
    }

    @Override
    public Namespace getNamespace(String name) {
        return kubernetesClient.resources(Namespace.class).withName(name).get();
    }
}
