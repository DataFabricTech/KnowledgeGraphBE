package com.tmax.datafabric.domain;

public interface CategoryRepository {

    Meta findMeta();

    String findRoot();

    String findByIdToString(String id);

    String findByDataObjectIdToString(String dataObjectId);
}