package com.tmax.datafabric.kubernetesclient.rbac.secret;

import io.fabric8.kubernetes.api.model.Secret;

public interface CreateSecret {
    Secret createOrReplaceSecret(String namespace, String secretName, String type);
}
