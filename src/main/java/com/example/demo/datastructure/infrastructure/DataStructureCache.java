package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.DataStructure;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataStructureCache {

    private final Map<String, DataStructure> dataStructureByName;

    public DataStructureCache() {
        this.dataStructureByName = new HashMap<>();
    }

    public void save(DataStructure dataStructure) {
        dataStructureByName.put(dataStructure.name(), dataStructure);
    }

    public DataStructure findByName(String dataStructureName) {
        return dataStructureByName.get(dataStructureName);
    }
}
