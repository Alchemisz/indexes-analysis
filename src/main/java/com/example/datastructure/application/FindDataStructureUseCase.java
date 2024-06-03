package com.example.datastructure.application;

import com.example.datastructure.client.dto.DataStructureDTO;
import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.infrastructure.DataStructureCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.datastructure.client.dto.DataStructureDTO.DataStructureElementDTO;

@Service
@RequiredArgsConstructor
public class FindDataStructureUseCase {

    private final DataStructureCache dataStructureCache;

    public DataStructureDTO findByName(String dataStructureName) {
        DataStructure dataStructure = dataStructureCache.findByName(dataStructureName);
        return buildDataStructureDTO(dataStructure);
    }

    private static DataStructureDTO buildDataStructureDTO(DataStructure dataStructure) {
        return new DataStructureDTO(
            dataStructure.name(),
            dataStructure.dataStructureElements().stream()
                .map(entry ->
                    new DataStructureElementDTO(
                        entry.getName(),
                        entry.getDataType(),
                        entry.getIndex() != null ? new DataStructureDTO.IndexDTO(entry.getIndex().name(), entry.getIndex().indexType()) : null,
                        entry.getRelatedDataStructure() != null
                            ? buildDataStructureDTO(entry.getRelatedDataStructure())
                            : null
                    )
                ).toList()
        );
    }


}
