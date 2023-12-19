package com.example.datafabric.application.category.service;

import com.example.datafabric.application.category.port.GetCategoryResultUseCase;
import com.example.datafabric.domain.Category;
import com.example.datafabric.domain.CategoryRepository;
import com.example.datafabric.domain.CategoryResult;
import com.example.datafabric.domain.DataObjectRepository;
import com.example.datafabric.domain.Meta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCategoryResultService implements GetCategoryResultUseCase {

    private final CategoryRepository categoryRepository;
    private final DataObjectRepository dataObjectRepository;

    @Override
    public CategoryResult get() {
        Meta meta = categoryRepository.findMeta();

        String targetCategoryString = categoryRepository.findRoot();
        Category targetCategory = parseCategory(targetCategoryString);

        for (Category child : targetCategory.getChildren()) {
            String childCategoryString = categoryRepository.findByIdToString(child.getId());
            Category childCategory = parseCategory(childCategoryString);

            child.setChildren(childCategory.getChildren());
            for (Category grandChild : child.getChildren()) {
                String grandChildCategoryString = categoryRepository.findByIdToString(
                    grandChild.getId());
                Category grandChildCategory = parseCategory(grandChildCategoryString);
                grandChild.setDoList(grandChildCategory.getDoList());
            }
        }
        CategoryResult result = CategoryResult.create(meta, targetCategory);
        return result;
    }

    @Override
    public CategoryResult getByDataObject(String dataObjectId, Integer nodeMax) {
        // TODO : dataObjectId validation check
        Meta meta = categoryRepository.findMeta();

        String targetCategoryString = categoryRepository.findByDataObjectIdToString(dataObjectId);
        if (targetCategoryString == null) {
            throw new RuntimeException("DataObject Id Cannot Found.");
        }
        Category targetCategory = parseCategory(targetCategoryString);

        if (targetCategory.getParentId() == null) {
            throw new RuntimeException("Cannot Found ParentID In Result of Category Analysis");
        }
        Category parentCategory = Category.translateFromString(
            categoryRepository.findByIdToString(targetCategory.getParentId()),
            List.of(targetCategory), null, false);

        if (parentCategory.getParentId() == null) {
            throw new RuntimeException("Cannot Found ParentID In Result of Category Analysis");
        }
        Category rootCategory = Category.translateFromString(
            categoryRepository.findByIdToString(parentCategory.getParentId()),
            List.of(parentCategory), null,
            false);

        return CategoryResult.create(meta, rootCategory);
    }

    @Override
    public CategoryResult getByCategory(String categoryId, Integer nodeMax) {
        Meta meta = categoryRepository.findMeta();

        String targetCategoryString = categoryRepository.findByIdToString(categoryId);
        if (targetCategoryString == null) {
            throw new RuntimeException("Category Id Cannot Found.");
        }
        Category targetCategory = parseCategory(targetCategoryString);

        return CategoryResult.create(meta, targetCategory);
    }

    private Category parseCategory(String categoryString) {
        List<String> categoryData = List.of(categoryString.split(","));
        Category category = Category.create(categoryData.get(0), categoryData.get(1),
            categoryData.get(2), null, null,
            true);
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(categoryString);
        int count = 0;

        while (matcher.find()) {
            count++;
            List<String> list = Arrays.stream(matcher.group(1).split(",")).map(String::trim)
                .collect(Collectors.toList());
            if (count == 1 && !list.get(0).equals("")) {
                for (String id : list) {
                    List<String> childData = List.of(
                        categoryRepository.findByIdToString(id).split(","));
                    category.addChild(
                        Category.create(childData.get(0), childData.get(1), childData.get(2), null,
                            null, false));
                }
            } else if (count == 2 && !list.get(0).equals("")) {
                for (String id : list) {
                    category.addDataObject(dataObjectRepository.findById(id));
                }
            } else {
                // error
            }
        }

        return category;
    }

    public void demo() {
        CategoryResult result = get();
        File file = new File("/home/jy/datafabric/Demo-Data/Category-result", "Category.json");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(file, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
