package com.example.datafabric.infrastructure.dataObejct;

import com.example.datafabric.application.config.Configuration;
import com.example.datafabric.domain.DataObject;
import com.example.datafabric.domain.DataObjectRepository;
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
public class DataObjectRepositoryAdapter implements DataObjectRepository {

    private final Configuration configuration;

    @Override
    public List<String> find() {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(
            Path.of(configuration.analysisResultPath, configuration.dataObjectResultFile).toFile()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public DataObject findById(String id) {
        DataObject dataObject = null;
        try (BufferedReader br = new BufferedReader(
            new FileReader(Path.of(configuration.analysisResultPath, configuration.dataObjectResultFile).toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> data = List.of(line.split(","));
                if (id.trim().equals(data.get(0))) {
                    dataObject = DataObject.create(data.get(0), data.get(1), data.get(2), data.get(3));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataObject;
    }
}
