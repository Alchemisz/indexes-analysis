package com.example.demo.datastructure.domain;

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
                        entry.relatedDataStructure() != null
                            ? create(entry.relatedDataStructure())
                            : null
                    )
                ).toList()
        );
    }


}
