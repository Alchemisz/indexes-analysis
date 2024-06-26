package com.example.datastructure.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class DataStructureElement {
    private final String name;
    private final DataType dataType;
    private final Integer length;
    private Index index;
    private final DataStructure relatedDataStructure;


    public DataStructureElement(String name, DataType dataType, Integer length, DataStructure dataStructure) {
        this.name = name;
        this.dataType = dataType;
        this.length = length;
        this.index = null;
        this.relatedDataStructure = dataStructure;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public void removeIndex() {
        this.index = null;
    }
}
