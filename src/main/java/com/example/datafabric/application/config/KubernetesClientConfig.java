package com.example.datafabric.application.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class KubernetesClientConfig {

    @Bean
    public KubernetesClient kubernetesClient() {
        Config config = new ConfigBuilder().build();
        KubernetesClient kubernetesClient = new KubernetesClientBuilder().withConfig(config).build();

        return kubernetesClient;
    }

}