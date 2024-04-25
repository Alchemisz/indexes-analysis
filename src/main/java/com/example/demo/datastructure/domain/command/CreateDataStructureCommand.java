package com.example.demo.datastructure.domain.command;

import com.example.demo.datastructure.domain.DataType;

import java.util.List;


public record CreateDataStructureCommand(
    DataStructure dataStructure
) {

    public record DataStructure(
        String name,
        List<Element> dataStructureElements
    ) {
    }

    public record Element(
        String name,
        DataType dataType,
        DataStructure relatedDataStructure
    ) {
    }

}
