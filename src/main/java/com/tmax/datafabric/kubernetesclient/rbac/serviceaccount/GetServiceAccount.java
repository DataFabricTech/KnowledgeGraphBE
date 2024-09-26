package com.tmax.datafabric.kubernetesclient.rbac.serviceaccount;

import io.fabric8.kubernetes.api.model.ServiceAccount;

public interface GetServiceAccount {
    ServiceAccount getServiceAccount(String namespace, String serviceAccountName);
}
