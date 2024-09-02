package com.tmax.datafabric.application.category.port;

import com.tmax.datafabric.domain.CategoryResult;

public interface GetCategoryResultUseCase {

    CategoryResult get();

    CategoryResult getByDataObject(String dataObjectId, Integer nodeMax);

    CategoryResult getByCategory(String categoryId, Integer nodeMax);
}
