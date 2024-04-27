package com.example.demo.datastructure.application;

import com.example.demo.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.DataStructureCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateIndexUseCase {

    private final DataStructureCache dataStructureCache;

    public void createForDataStructure(CreateIndexCommandDTO commandDTO) {
        //TODO zrobić clean code póki się da005
        //TODO zrobić obsługe indeksów dla wielu pól
        DataStructure dataStructure = dataStructureCache.findByName(commandDTO.dataStructureName());
        dataStructure.createIndex(commandDTO);
    }

}
