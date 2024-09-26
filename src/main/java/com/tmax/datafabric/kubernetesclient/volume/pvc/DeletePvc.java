package com.tmax.datafabric.kubernetesclient.volume.pvc;

public interface DeletePvc {
    void deletePvc(String namespace, String pvcName);
}
