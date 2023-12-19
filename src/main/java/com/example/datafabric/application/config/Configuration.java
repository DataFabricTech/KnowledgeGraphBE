package com.example.datafabric.application.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "analysis-result")
public class Configuration {

    public final String analysisResultPath;
    public final String categoryResultFile;
    public final String dataObjectResultFile;
    public final String relationResultFile;

    public Configuration(String path, String categoryResultFile, String dataObjectResultFile,
        String relationResultFile) {
        this.analysisResultPath = path;
        this.categoryResultFile = categoryResultFile;
        this.dataObjectResultFile = dataObjectResultFile;
        this.relationResultFile = relationResultFile;
    }
}
