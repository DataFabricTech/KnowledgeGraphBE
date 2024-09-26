package com.tmax.datafabric.kubernetesclient.namespace;

import io.fabric8.kubernetes.api.model.Namespace;

public interface GetNamespace {
    Namespace getNamespace(String name);
}
