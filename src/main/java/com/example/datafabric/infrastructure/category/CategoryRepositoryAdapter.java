package com.example.datafabric.infrastructure.category;

import com.example.datafabric.application.config.Configuration;
import com.example.datafabric.domain.CategoryRepository;
import com.example.datafabric.domain.Meta;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final Configuration configuration;

    @Override
    public Meta findMeta() {
        String line;
        try (BufferedReader reader = new BufferedReader(
            new FileReader(
                Path.of(configuration.analysisResultPath, configuration.categoryResultFile)
                    .toFile()))) {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Meta.translateFromString(line);
    }

    @Override
    public String findRoot() {
        try (BufferedReader reader = new BufferedReader(new FileReader(
            Path.of(configuration.analysisResultPath, configuration.categoryResultFile)
                .toFile()))) {
            reader.readLine();
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findByIdToString(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(
            Path.of(configuration.analysisResultPath, configuration.categoryResultFile)
                .toFile()))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (id.equals(List.of(line.split(",")).get(0))) {
                    return line;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public String findByDataObjectIdToString(String dataObjectId) {
        try (BufferedReader reader = new BufferedReader(
            new FileReader(
                Path.of(configuration.analysisResultPath, configuration.categoryResultFile)
                    .toFile()))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                Matcher matcher = pattern.matcher(line);
                int count = 0;
                while (matcher.find()) {
                    count++;
                    if (count == 2 && Arrays.stream(matcher.group(1).split(","))
                        .map(String::trim)
                        .anyMatch(dataObjectId::equals)) {
                        return line;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
