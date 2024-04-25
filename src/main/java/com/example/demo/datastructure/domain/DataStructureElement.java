package com.example.demo.datastructure.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DataStructureElement {
    private final String name;
    private final DataType dataType;
    private final DataStructure relatedDataStructure;


    public DataStructureElement(String name, DataType dataType, DataStructure dataStructure) {
        this.name = name;
        this.dataType = dataType;
        this.relatedDataStructure = dataStructure;
    }

}
