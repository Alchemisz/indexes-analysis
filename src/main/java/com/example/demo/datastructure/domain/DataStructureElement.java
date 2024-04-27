package com.example.demo.datastructure.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DataStructureElement {
    private final String name;
    private final DataType dataType;
    private Index index;
    private final DataStructure relatedDataStructure;


    public DataStructureElement(String name, DataType dataType, DataStructure dataStructure) {
        this.name = name;
        this.dataType = dataType;
        this.index = null;
        this.relatedDataStructure = dataStructure;
    }

    public void setIndex(Index index) {
        this.index = index;
    }
}
