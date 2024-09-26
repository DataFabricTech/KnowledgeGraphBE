package com.tmax.datafabric.kubernetesclient.volume.pvc;

import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.ResourceRequirements;
import java.util.List;

public interface CreatePvc {
    PersistentVolumeClaim createOrReplacePvc(String namespace, String pvcName,
        ResourceRequirements resourceRequirements, List<String> accessModes);
}
