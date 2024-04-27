package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.DataStructure;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class ExtractSubStructureService {

    public DataStructures extractDataStructures(DataStructure dataStructure) {
        List<DataStructure> extractedDataStructures = new ArrayList<>();

        List<DataStructure> dataStructures = new ArrayList<>();
        DataStructure current = dataStructure;

        do {
            current.dataStructureElements().forEach(dataStructureElement -> {
                if (dataStructureElement.getRelatedDataStructure() != null) {
                    dataStructures.add(dataStructureElement.getRelatedDataStructure());
                }
            });
            dataStructures.remove(current);
            extractedDataStructures.add(current);
            if (!dataStructures.isEmpty()) {
                current = dataStructures.get(dataStructures.size() - 1);
            } else {
                current = null;
            }
        } while (current != null);

        return DataStructures.of(extractedDataStructures);
    }

}
