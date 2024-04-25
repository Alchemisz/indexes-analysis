package com.example.demo.datastructure.client.dto;

import com.example.demo.datastructure.domain.DataType;

import java.util.List;


public record CreateDataStructureCommandDTO(
    DataStructureDTO dataStructure
) {

    public record DataStructureDTO(
        String name,
        List<DataStructureElementDTO> dataStructureElements
    ) {
    }

    public record DataStructureElementDTO(
        String name,
        DataType dataType,
        DataStructureDTO relatedDataStructure
    ) {
    }

}

