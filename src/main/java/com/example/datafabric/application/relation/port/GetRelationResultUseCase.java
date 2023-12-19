package com.example.datafabric.application.relation.port;

import com.example.datafabric.domain.RelationResult;

public interface GetRelationResultUseCase {

    RelationResult getByDataObject(String dataObjectId, Integer nodeMax);

}
