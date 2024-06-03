package com.example.datastructure.infrastructure;

import com.example.datastructure.domain.DataStructure;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataStructureCache {

    private final ExtractSubStructureService extractSubStructureService;
    private final Map<String, DataStructure> dataStructureByName;

    public DataStructureCache(ExtractSubStructureService extractSubStructureService) {
        this.extractSubStructureService = extractSubStructureService;
        this.dataStructureByName = new HashMap<>();
    }

    public void save(DataStructure dataStructure) {
        var dataStructures = extractSubStructureService.extractDataStructures(dataStructure);
        dataStructureByName.putAll(dataStructures.dataStructureByName());
    }

    public DataStructure findByName(String dataStructureName) {
        return dataStructureByName.get(dataStructureName);
    }
}
