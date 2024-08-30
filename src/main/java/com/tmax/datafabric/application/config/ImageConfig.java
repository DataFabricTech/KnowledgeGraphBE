package com.tmax.datafabric.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "image")
public class ImageConfig {

    public final String trainImage;
    public final String inferenceImage;

    public ImageConfig(String trainImage, String inferenceImage) {
        this.trainImage = trainImage;
        this.inferenceImage = inferenceImage;
    }
}
