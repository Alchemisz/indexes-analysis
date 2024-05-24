package com.example.demo.datastructure.domain;

import com.example.demo.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.demo.datastructure.domain.command.CreateDataStructureCommand;

import java.util.List;

public record DataStructure(
    String name,
    List<DataStructureElement> dataStructureElements
) {

    public static DataStructure create(CreateDataStructureCommand command) {
        return create(command.dataStructure());
    }

    private static DataStructure create(CreateDataStructureCommand.DataStructure dataStructure) {
        return new DataStructure(
            dataStructure.name(),
            dataStructure.dataStructureElements().stream()
                .map(entry ->
                    new DataStructureElement(
                        entry.name(),
                        entry.dataType(),
                        entry.length(),
                        entry.relatedDataStructure() != null
                            ? create(entry.relatedDataStructure())
                            : null
                    )
                ).toList()
        );
    }


    public void createIndex(CreateIndexCommandDTO commandDTO) {
        DataStructureElement dataStructureElement = dataStructureElements.stream()
            .filter(entry -> commandDTO.dataStructureElementNames().contains(entry.getName()))
            .findFirst()
            .orElseThrow();
        dataStructureElement.setIndex(new Index(commandDTO.indexName(), commandDTO.indexType()));
    }

    public void removeIndex(String indexName) {
        dataStructureElements.stream()
            .filter(dataStructureElement -> dataStructureElement.getIndex() != null && dataStructureElement.getIndex().name().equals(indexName))
            .forEach(DataStructureElement::removeIndex);
    }
}
