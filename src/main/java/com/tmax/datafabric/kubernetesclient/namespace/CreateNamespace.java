package com.tmax.datafabric.kubernetesclient.namespace;

import io.fabric8.kubernetes.api.model.Namespace;

public interface CreateNamespace {
    Namespace createOrReplaceNamespace(String name);

    Namespace createOrReplaceNamespace(Namespace namespace);
}
