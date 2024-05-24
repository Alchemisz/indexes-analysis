package com.example.demo.datastructure.domain.command;

import com.example.demo.datastructure.domain.DataType;

import java.util.List;


public record CreateDataStructureCommand(
    DataStructure dataStructure
) {

    public record DataStructure(
        String name,
        List<DataStructureElement> dataStructureElements
    ) {
    }

    public record DataStructureElement(
        String name,
        DataType dataType,
        Integer length,
        DataStructure relatedDataStructure
    ) {
    }

}

