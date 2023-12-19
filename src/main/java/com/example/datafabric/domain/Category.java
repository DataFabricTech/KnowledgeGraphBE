package com.example.datafabric.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private String id;
    private String name;
    private String parentId;
    private List<Category> children = new ArrayList<>();
    private List<DataObject> doList = new ArrayList<>();
    private boolean hasTarget;

    private Category(String id, String name, String parentId, List<Category> children, List<DataObject> doList,
        boolean hasTarget) {
        this.id = id;
        this.name = name;
        this.parentId = parentId != null ? parentId : null;
        if (children != null) {
            this.children.addAll(children);
        }
        if (doList != null) {
            this.doList.addAll(doList);
        }
        this.hasTarget = hasTarget;
    }

    public static Category create(String id, String name, String parentId, List<Category> children,
        List<DataObject> doList, boolean hasTarget) {
        return new Category(id, name, parentId, children, doList, hasTarget);
    }

    public static Category translateFromString(String category, List<Category> children,
        List<DataObject> doList, boolean hasTarget) {
        List<String> parentInfo = List.of(category.split(","));
        return Category.create(parentInfo.get(0), parentInfo.get(1), parentInfo.get(2), children,
            doList, hasTarget);
    }

    public Category addChild(Category child) {
        this.children.add(child);
        return this;
    }

    public Category addDataObject(DataObject dataObject) {
        this.doList.add(dataObject);
        return this;
    }
}
