package com.example.demo.datastructure.domain;

import com.example.demo.datastructure.domain.command.CreateDataStructureCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataStructureTest {

    @Test
    void success_create_simple_data_structure() {
        //given
        var command = new CreateDataStructureCommand(
            new CreateDataStructureCommand.DataStructure(
                "Person",
                List.of(
                    new CreateDataStructureCommand.DataStructureElement(
                        "Name",
                        DataType.STRING,
                        null
                    ),
                    new CreateDataStructureCommand.DataStructureElement(
                        "Age",
                        DataType.NUMBER,
                        null
                    )
                )
            )
        );

        var expectedResult = new DataStructure(
            "Person",
            List.of(
                new DataStructureElement("Name", DataType.STRING, null),
                new DataStructureElement("Age", DataType.NUMBER, null)
            )
        );

        //when
        var result = DataStructure.create(command);

        //then
        assertThat(result)
            .isEqualTo(expectedResult);
    }

    @Test
    void success_create_tree_data_structure() {
        //given
        CreateDataStructureCommand.DataStructure addressDataStructure = new CreateDataStructureCommand.DataStructure(
            "Adress",
            List.of(
                new CreateDataStructureCommand.DataStructureElement("id", DataType.NUMBER, null),
                new CreateDataStructureCommand.DataStructureElement("city", DataType.STRING, null)
            )
        );

        CreateDataStructureCommand.DataStructure personDataStructure = new CreateDataStructureCommand.DataStructure(
            "Person",
            List.of(
                new CreateDataStructureCommand.DataStructureElement("Address_Id", DataType.STRING, addressDataStructure),
                new CreateDataStructureCommand.DataStructureElement("Age", DataType.NUMBER, null)
            )
        );
        var command = new CreateDataStructureCommand(personDataStructure);

        var expectedResult = new DataStructure(
            "Person",
            List.of(
                new DataStructureElement("Address_Id", DataType.STRING,
                    new DataStructure(
                        "Address",
                        List.of(
                            new DataStructureElement("id", DataType.NUMBER, null),
                            new DataStructureElement("city", DataType.STRING, null)
                        )
                    )
                ),
                new DataStructureElement("Age", DataType.NUMBER, null)
            )
        );

        //when
        var result = DataStructure.create(command);

        //then
        assertThat(result)
            .isEqualTo(expectedResult);
    }

}