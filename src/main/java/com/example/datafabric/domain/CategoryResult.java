package com.example.datafabric.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResult {

    private Meta meta;
    private Category category;

    public static CategoryResult create(Meta meta,Category category){
        return new CategoryResult(meta,category);
    }
}