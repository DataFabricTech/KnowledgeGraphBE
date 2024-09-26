package com.tmax.datafabric.kubernetesclient.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "kubernetes.config")
public class KubernetesConfig {
    private final String type;
    private final String podName;
    private final String pvcName;
    private final String namespace;
    private final String argoServiceaccountName;
    private final String imagePullSecret;
    private final String mountPath;

    public KubernetesConfig(String type, String namespace, String argoServiceaccountName,
        String podName, String pvcName, String imagePullSecret, String mountPath) {
        this.type = type;
        this.namespace = namespace;
        this.argoServiceaccountName = argoServiceaccountName;
        this.podName = podName;
        this.pvcName = pvcName;
        this.imagePullSecret = imagePullSecret;
        this.mountPath = mountPath;
    }
}
