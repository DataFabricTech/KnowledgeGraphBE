package com.tmax.datafabric.domain;

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