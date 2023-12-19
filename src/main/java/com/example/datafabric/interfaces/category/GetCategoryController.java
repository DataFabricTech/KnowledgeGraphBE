package com.example.datafabric.interfaces.category;

import com.example.datafabric.application.category.port.GetCategoryResultUseCase;
import com.example.datafabric.domain.CategoryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/datafabric/category")
@RestController
@RequiredArgsConstructor
public class GetCategoryController {

    private final GetCategoryResultUseCase getCategoryResultUseCase;

    @GetMapping("/byDataObject")
    public CategoryResult getByDataObjectName(
        @RequestParam(value = "dataObjectId", required = true) String dataObjectId,
        @RequestParam(value = "nodeMax", required = false) Integer nodeMax) {
        return getCategoryResultUseCase.getByDataObject(dataObjectId, nodeMax);
    }

    @GetMapping("/byCategory")
    public CategoryResult getByCategory(
        @RequestParam(value = "categoryId", required = true) String categoryId,
        @RequestParam(value = "nodeMax", required = false) Integer nodeMax) {
        return getCategoryResultUseCase.getByCategory(categoryId, nodeMax);
    }
}
