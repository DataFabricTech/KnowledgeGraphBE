package com.example.datafabric.application.category.port;

import com.example.datafabric.domain.CategoryResult;

public interface GetCategoryResultUseCase {

    CategoryResult getByDataObject(String dataObjectId, Integer nodeMax);

    CategoryResult getByCategory(String categoryId, Integer nodeMax);
}
