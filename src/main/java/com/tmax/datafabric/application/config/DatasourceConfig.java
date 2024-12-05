package com.tmax.datafabric.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "datasource.config")
public class DatasourceConfig {
    private final String path;

    public DatasourceConfig(String path) {
        this.path = path;
    }
}