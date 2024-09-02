package com.tmax.datafabric.application.relation.port;

import com.tmax.datafabric.domain.RelationResult;

public interface GetRelationResultUseCase {

    RelationResult getByDataObject(String dataObjectId, Integer nodeMax);

}
