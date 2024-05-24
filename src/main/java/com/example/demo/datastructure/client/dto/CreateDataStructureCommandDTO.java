package com.example.demo.datastructure.client.dto;

import com.example.demo.datastructure.domain.DataType;

import java.util.List;


public record CreateDataStructureCommandDTO(
    DataStructureDTO dataStructure,
    boolean createInDatabase
) {

    public record DataStructureDTO(
        String name,
        List<DataStructureElementDTO> dataStructureElements
    ) {
    }

    public record DataStructureElementDTO(
        String name,
        DataType dataType,
        Integer length,
        DataStructureDTO relatedDataStructure
    ) {
    }

}

