package com.tmax.datafabric.application.analysis.service;

import com.tmax.datafabric.application.analysis.port.GetAnalysisResultNodeUseCase;
import com.tmax.datafabric.application.analysis.port.dto.DataRelationDto;
import com.tmax.datafabric.application.exception.InvalidAnalysisIdException;
import com.tmax.datafabric.domain.DataObject;
import com.tmax.datafabric.domain.Edge;
import com.tmax.datafabric.domain.Meta;
import com.tmax.datafabric.domain.RelationResult;
import com.tmax.datafabric.domain.analysis.Analysis;
import com.tmax.datafabric.domain.analysis.AnalysisRepository;
import com.tmax.datafabric.kubernetesclient.config.KubernetesConfig;
import com.tmax.datafabric.kubernetesclient.pod.KubernetesPodClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAnalysisResultNodeService implements GetAnalysisResultNodeUseCase {
    private final AnalysisRepository analysisRepository;
    private final KubernetesPodClient kubernetesPodClient;
    private final KubernetesConfig kubernetesConfig;
    @Override
    public RelationResult getRelationResultNodes(Long analysisId, String dataId, String modelType) {
        Analysis analysis = analysisRepository.findById(analysisId).orElseThrow(InvalidAnalysisIdException::new);


        if (Optional.ofNullable(analysis.getFinishedAt()).isEmpty()) {
            throw new InvalidAnalysisIdException();
        }

        List<DataRelationDto> nodeDataList = new ArrayList<>();

        String filePath = String.format("/pvc/mnt/analysis-%d/analysis_result.csv",
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
                if ((relationRow[0].equals(dataId)) && (Double.parseDouble(relationRow[2]) > 0)) {
                    DataRelationDto dataRelationDto = DataRelationDto.builder()
                            .dataId(relationRow[1])
                            .score(Double.valueOf(relationRow[2]))
                            .build();
                    nodeDataList.add(dataRelationDto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Meta meta = Meta.create(analysis.getFinishedAt(), analysis.getFinishedAt(), analysis.getSolutionType(),
               Arrays.asList("CosineSimilarity")); // TODO : Algorithm 다양화에 따른 향후 처리 필요

        List<DataObject> dataObjectList = new ArrayList<>();
        List<Edge> edgeList = new ArrayList<>();

        dataObjectList.add(fetchDataObjectMeta(analysisId, dataId));

        for (DataRelationDto dataRelationDto: nodeDataList) {
            dataObjectList.add(fetchDataObjectMeta(analysisId, dataRelationDto.getDataId()));
            edgeList.add(Edge.create(dataId, dataRelationDto.getDataId(), dataRelationDto.getScore().floatValue()));
        }

        return RelationResult.createRelationResult(meta, dataObjectList, edgeList);
    }

    private DataObject fetchDataObjectMeta(Long analysisId, String dataId) {
        String metaFilePath = String.format("/pvc/mnt/analysis-%d/meta/%s.json",
                analysisId, dataId);

        DataObject dataObject = null;

        try (InputStream inputStream = kubernetesPodClient.downloadFileFromPod(metaFilePath,
                kubernetesConfig.getNamespace(), kubernetesConfig.getPodName())) {
            String jsonString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(jsonString);
            // TODO 빈 json 처리?
            String name = jsonObject.get("name").toString();
            String type = jsonObject.get("serviceType").toString();
            String user = jsonObject.get("updatedBy").toString();
            dataObject = DataObject.create(dataId, name, type, user);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return DataObject.create(dataId, dataId, "", "");
        }

        return dataObject;
    }
}
