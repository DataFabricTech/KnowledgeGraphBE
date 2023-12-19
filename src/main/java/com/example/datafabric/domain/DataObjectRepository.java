package com.example.datafabric.domain;

import java.util.List;

public interface DataObjectRepository {
    List<String> find();

    DataObject findById(String id);
}
