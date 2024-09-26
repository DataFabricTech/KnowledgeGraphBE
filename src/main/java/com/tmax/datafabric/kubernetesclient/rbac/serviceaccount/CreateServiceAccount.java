package com.tmax.datafabric.kubernetesclient.rbac.serviceaccount;

import io.fabric8.kubernetes.api.model.ServiceAccount;

public interface CreateServiceAccount {
    ServiceAccount createOrReplaceServiceAccount(String namespace, String serviceAccountName,
        String secretName);
}
