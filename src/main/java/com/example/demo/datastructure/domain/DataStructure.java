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
        dataStructureElements.stream()
            .filter(element -> commandDTO.dataStructureElementNames().contains(element.getName()))
            .forEach(entry -> entry.setIndex(
                new Index(commandDTO.indexName(), commandDTO.indexType()))
            );
    }

    public void removeIndex(String indexName) {
        dataStructureElements.stream()
            .filter(element -> element.getIndex() != null && element.getIndex().name().equals(indexName))
            .forEach(DataStructureElement::removeIndex);
    }
}
