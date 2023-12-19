package com.example.datafabric.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public class DataObject {

    private String id;
    private String name;
    private String type;
    private String user;
    //private String dataSource;


    public static DataObject create(String id, String name, String type, String user) {
        return new DataObject(id, name, type, user);
    }
}
