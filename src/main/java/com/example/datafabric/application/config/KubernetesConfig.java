package com.example.datafabric.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "kubernetes")
public class KubernetesConfig {
    private final String namespace;
    private final String argoServiceaccountName;
    private final String imagePullSecret;
    private final String mountPath;

    public KubernetesConfig(String namespace, String argoServiceaccountName, String imagePullSecret, String mountPath) {
        this.namespace = namespace;
        this.argoServiceaccountName = argoServiceaccountName;
        this.imagePullSecret = imagePullSecret;
        this.mountPath = mountPath;
    }
}
