package com.example.datafabric.domain;

import java.util.List;

public interface RelationRepository {

    List<String> find();

    List<String> findByTargetIdToString(String id);
}
