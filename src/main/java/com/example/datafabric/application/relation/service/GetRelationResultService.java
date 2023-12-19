package com.example.datafabric.application.relation.service;

import com.example.datafabric.application.relation.port.GetRelationResultUseCase;
import com.example.datafabric.domain.DataObject;
import com.example.datafabric.domain.DataObjectRepository;
import com.example.datafabric.domain.Edge;
import com.example.datafabric.domain.Meta;
import com.example.datafabric.domain.RelationRepository;
import com.example.datafabric.domain.RelationResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetRelationResultService implements GetRelationResultUseCase {

    private final DataObjectRepository dataObjectRepository;
    private final RelationRepository relationRepository;

    @Override
    public RelationResult getByDataObject(String dataObjectId, Integer nodeMax) {

        List<String> results = relationRepository.find();
        Meta meta = Meta.translateFromString(results.remove(0));

        Set<String> nodeIds = new HashSet<>();
        nodeIds.add(dataObjectId);

        List<Edge> edges = new ArrayList<>();
        for (String relation : relationRepository.findByTargetIdToString(dataObjectId)) {
            List<String> edge = List.of(relation.split(","));
            edges.add(Edge.create(edge.get(0), edge.get(1), Float.valueOf(edge.get(2))));
            nodeIds.add(edge.get(0).equals(dataObjectId) ? edge.get(1) : edge.get(0));
        }

        List<DataObject> nodes = new ArrayList<>();
        for (String dataObject : dataObjectRepository.find()) {
            List<String> node = List.of(dataObject.split(","));
            if (nodeIds.contains(node.get(0))) {
                nodes.add(DataObject.create(node.get(0), node.get(1), node.get(2), node.get(3)));
            }
        }

        RelationResult result = RelationResult.createRelationResult(meta, nodes, edges);
        
        return result;
    }

}
