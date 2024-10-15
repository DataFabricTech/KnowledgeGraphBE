package com.tmax.datafabric.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "image")
public class ImageConfig {
    private final String analysisImageName;
    private final String downloaderImageName;

    public ImageConfig(String analysisImageName, String downloaderImageName) {
        this.analysisImageName = analysisImageName;
        this.downloaderImageName = downloaderImageName;
    }
}
