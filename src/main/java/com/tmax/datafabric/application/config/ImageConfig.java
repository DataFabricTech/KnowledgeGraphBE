package com.tmax.datafabric.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "image")
public class ImageConfig {
    private final String trainImageName;
    private final String inferenceImageName;
    private final String downloaderImageName;

    public ImageConfig(String trainImageName, String inferenceImageName, String downloaderImageName) {
        this.trainImageName = trainImageName;
        this.inferenceImageName = inferenceImageName;
        this.downloaderImageName = downloaderImageName;
    }
}
