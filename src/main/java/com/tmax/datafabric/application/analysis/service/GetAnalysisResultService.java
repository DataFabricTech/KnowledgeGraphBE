package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.GetAnalysisResultUseCase;
import com.tmax.datafabric.application.analysis.port.dto.DataRelationData;
import com.tmax.datafabric.application.analysis.port.dto.DataRelationDto;
import com.tmax.datafabric.application.exception.InvalidAnalysisIdException;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.pod.KubernetesPodClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAnalysisResultService implements GetAnalysisResultUseCase {
    private final AnalysisRepository analysisRepository;
    private final KubernetesPodClient kubernetesPodClient;
    private final KubernetesConfig kubernetesConfig;

    @Override
    public DataRelationData getRelation(Long analysisId, Long dataId, String modelType) {

//        analysisRepository.findById(analysisId).orElseThrow(InvalidAnalysisIdException::new);

        List<DataRelationDto> result = new ArrayList<>();

        String filePath = String.format("/pvc/mnt/analysis-%d/association_rule_result.csv",
            analysisId);

        InputStream inputStream = kubernetesPodClient.downloadFileFromPod(filePath,
            kubernetesConfig.getNamespace(), kubernetesConfig.getPodName());

        try (InputStreamReader streamReader =
            new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {

            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] relationRow = line.split(",");
                if (Long.valueOf(relationRow[0]).equals(dataId)) {
                    DataRelationDto dataRelationDto = DataRelationDto.builder()
                        .dataId(Long.valueOf(relationRow[1]))
                        .score(Double.valueOf(relationRow[2]))
                        .build();
                    result.add(dataRelationDto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return DataRelationData.from(result);
    }
}
