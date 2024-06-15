package com.example.datastructure.infrastructure;

import com.example.datastructure.domain.DataStructure;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataStructureCache {

    private final ExtractStructuresService extractStructuresService;
    private final Map<String, DataStructure> dataStructureByName;

    public DataStructureCache(ExtractStructuresService extractStructuresService) {
        this.extractStructuresService = extractStructuresService;
        this.dataStructureByName = new HashMap<>();
    }

    public void save(DataStructure dataStructure) {
        var dataStructures = extractStructuresService.extractDataStructures(dataStructure);
        dataStructureByName.putAll(dataStructures.dataStructureByName());
    }

    public DataStructure findByName(String dataStructureName) {
        return dataStructureByName.get(dataStructureName);
    }
}
