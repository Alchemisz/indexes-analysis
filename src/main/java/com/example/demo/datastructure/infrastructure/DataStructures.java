package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.DataStructure;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public record DataStructures(
    Map<String, DataStructure> dataStructureByName
) {

    static DataStructures of(List<DataStructure> dataStructures) {
        return new DataStructures(
            dataStructures.stream()
                .collect(Collectors.toMap(DataStructure::name, entry -> entry))
        );
    }

}
