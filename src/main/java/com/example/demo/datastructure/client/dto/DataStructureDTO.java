package com.example.demo.datastructure.client.dto;

import com.example.demo.datastructure.domain.DataType;
import com.example.demo.datastructure.domain.IndexType;

import java.util.List;


public record DataStructureDTO(
    String name,
    List<DataStructureElementDTO> dataStructureElements
) {

    public record DataStructureElementDTO(
        String name,
        DataType dataType,
        IndexDTO indexDTO,
        DataStructureDTO relatedDataStructure
    ) {
    }


    public record IndexDTO(
        String name,
        IndexType indexType
    ) {
    }

}
