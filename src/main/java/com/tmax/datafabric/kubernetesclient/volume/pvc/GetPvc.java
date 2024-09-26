package com.tmax.datafabric.kubernetesclient.volume.pvc;

import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;

public interface GetPvc {
    PersistentVolumeClaim getPvc(String namespace, String pvcName);
}
