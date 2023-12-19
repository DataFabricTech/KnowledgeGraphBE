package com.example.datafabric.domain;

public interface CategoryRepository {

    Meta findMeta();

    String findByIdToString(String id);

    String findByDataObjectIdToString(String dataObjectId);

}
