package com.example.datastructure.infrastructure;

import com.example.datastructure.domain.DataStructure;

import java.util.ArrayList;
import java.util.Collection;
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

    public List<DataStructure> getAllDataStructures() {
        return new ArrayList<>(this.dataStructureByName.values());
    }

}
