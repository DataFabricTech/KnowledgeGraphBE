package com.example.datafabric.infrastructure.relation;

import com.example.datafabric.application.config.Configuration;
import com.example.datafabric.domain.RelationRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelationRepositoryAdapter implements RelationRepository {

    private final Configuration configuration;

    @Override
    public List<String> find() {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(
            Path.of(configuration.analysisResultPath, configuration.relationResultFile).toFile()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<String> findByTargetIdToString(String id) {
        List<String> edgeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
            new FileReader(Path.of(configuration.analysisResultPath, configuration.relationResultFile).toFile()))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int sourceId = Integer.parseInt(parts[0]);

                if (sourceId > Integer.parseInt(id)) {
                    break;
                }
                if (id.equals(parts[1]) || id.equals(parts[0])) {
                    edgeList.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return edgeList;
    }
}
