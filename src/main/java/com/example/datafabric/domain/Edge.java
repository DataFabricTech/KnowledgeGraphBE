package com.example.datafabric.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Edge {

    private String sourceNodeId;
    private String targetNodeId;
    private Float score;
    //private String attribute;

    public static Edge create(String sourceNodeId, String targetNodeId, Float score) {
        return new Edge(sourceNodeId, targetNodeId, score);
    }
}
