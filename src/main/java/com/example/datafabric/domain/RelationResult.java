package com.example.datafabric.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelationResult {

    private Meta meta;
    private List<DataObject> nodes;
    private List<Edge> edges;


    public static RelationResult createRelationResult(Meta meta, List<DataObject> nodes, List<Edge> edges) {
        return new RelationResult(meta, nodes, edges);
    }

}
