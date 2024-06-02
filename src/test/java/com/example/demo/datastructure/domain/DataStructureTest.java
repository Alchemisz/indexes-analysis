package com.example.demo.datastructure.domain;

import com.example.demo.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.demo.datastructure.client.dto.RemoveIndexCommandDTO;
import com.example.demo.datastructure.domain.command.CreateDataStructureCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.example.demo.TestRandomUtils.getRandomString;
import static com.example.demo.datastructure.domain.FakeDataStructureBuilder.fakeDataStructureBuilder;
import static com.example.demo.datastructure.domain.FakeDataStructureElementBuilder.fakeDataStructureElementBuilder;
import static org.assertj.core.api.Assertions.assertThat;

class DataStructureTest {

    private static final Integer DEFAULT_LENGTH = 20;

    private static final DataStructureElement ID = fakeDataStructureElementBuilder()
        .withName("ID")
        .withDataType(DataType.NUMBER)
        .withIndex(null)
        .withRelatedDataStructure(null)
        .build();

    private static final DataStructureElement NAME = fakeDataStructureElementBuilder()
        .withName("NAME")
        .withDataType(DataType.STRING)
        .withLength(50)
        .withIndex(null)
        .withRelatedDataStructure(null)
        .build();

    private static final DataStructureElement AGE = fakeDataStructureElementBuilder()
        .withName("ID")
        .withDataType(DataType.NUMBER)
        .withIndex(null)
        .withRelatedDataStructure(null)
        .build();

    private static final String DATA_STRUCTURE_NAME = getRandomString(10);
    private static final String INDEX_NAME = getRandomString(10);

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
                        DEFAULT_LENGTH,
                        null
                    ),
                    new CreateDataStructureCommand.DataStructureElement(
                        "Age",
                        DataType.NUMBER,
                        DEFAULT_LENGTH,
                        null
                    )
                )
            )
        );

        var expectedResult = new DataStructure(
            "Person",
            List.of(
                new DataStructureElement("Name", DataType.STRING, DEFAULT_LENGTH, null),
                new DataStructureElement("Age", DataType.NUMBER, DEFAULT_LENGTH, null)
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
            "Address",
            List.of(
                new CreateDataStructureCommand.DataStructureElement("id", DataType.NUMBER, DEFAULT_LENGTH, null),
                new CreateDataStructureCommand.DataStructureElement("city", DataType.STRING, DEFAULT_LENGTH, null)
            )
        );

        CreateDataStructureCommand.DataStructure personDataStructure = new CreateDataStructureCommand.DataStructure(
            "Person",
            List.of(
                new CreateDataStructureCommand.DataStructureElement("Address_Id", DataType.STRING, DEFAULT_LENGTH, addressDataStructure),
                new CreateDataStructureCommand.DataStructureElement("Age", DataType.NUMBER, DEFAULT_LENGTH, null)
            )
        );
        var command = new CreateDataStructureCommand(personDataStructure);

        var expectedResult = new DataStructure(
            "Person",
            List.of(
                new DataStructureElement("Address_Id", DataType.STRING,
                    DEFAULT_LENGTH, new DataStructure(
                    "Address",
                    List.of(
                        new DataStructureElement("id", DataType.NUMBER, DEFAULT_LENGTH, null),
                        new DataStructureElement("city", DataType.STRING, DEFAULT_LENGTH, null)
                    )
                )
                ),
                new DataStructureElement("Age", DataType.NUMBER, DEFAULT_LENGTH, null)
            )
        );

        //when
        var result = DataStructure.create(command);

        //then
        assertThat(result)
            .isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("successCreateIndexParameters")
    void success_create_index(
        String useCase,
        DataStructure sut,
        CreateIndexCommandDTO commandDTO,
        DataStructure expected
    ) {
        //when && then
        sut.createIndex(commandDTO);

        assertThat(sut)
            .isEqualTo(expected);
    }

    private static Stream<Arguments> successCreateIndexParameters() {
        return Stream.of(
            Arguments.of(
                "Add index on single field",
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(ID, NAME, AGE)
                    .build(),
                new CreateIndexCommandDTO(
                    DATA_STRUCTURE_NAME,
                    Set.of(
                        ID.getName()
                    ),
                    INDEX_NAME,
                    IndexType.B_TREE
                ),
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(new Index(INDEX_NAME, IndexType.B_TREE))
                            .build(),
                        NAME,
                        AGE
                    )
                    .build()
            ),
            Arguments.of(
                "Add index on multiple fields",
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(ID, NAME, AGE)
                    .build(),
                new CreateIndexCommandDTO(
                    DATA_STRUCTURE_NAME,
                    Set.of(
                        ID.getName(),
                        NAME.getName()
                    ),
                    INDEX_NAME,
                    IndexType.COMPOSITE
                ),
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(new Index(INDEX_NAME, IndexType.COMPOSITE))
                            .build(),
                        fakeDataStructureElementBuilder(NAME)
                            .withIndex(new Index(INDEX_NAME, IndexType.COMPOSITE))
                            .build(),
                        AGE
                    ).build()
            )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("successRemoveIndexParameters")
    void success_remove_index(
        String useCase,
        DataStructure sut,
        RemoveIndexCommandDTO commandDTO,
        DataStructure expected
    ) {
        //when && then
        sut.removeIndex(commandDTO.indexName());

        assertThat(sut)
            .isEqualTo(expected);
    }

    private static Stream<Arguments> successRemoveIndexParameters() {
        return Stream.of(
            Arguments.of(
                "Remove index from single field",
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(new Index(INDEX_NAME, IndexType.B_TREE))
                            .build(),
                        NAME,
                        AGE
                    ).build(),
                new RemoveIndexCommandDTO(INDEX_NAME),
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(null)
                            .build(),
                        NAME,
                        AGE
                    )
                    .build()
            ),
            Arguments.of(
                "Remove index from multiple fields",
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(new Index(INDEX_NAME, IndexType.COMPOSITE))
                            .build(),
                        fakeDataStructureElementBuilder(NAME)
                            .withIndex(new Index(INDEX_NAME, IndexType.COMPOSITE))
                            .build(),
                        AGE
                    ).build(),
                new RemoveIndexCommandDTO(INDEX_NAME),
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(null)
                            .build(),
                        fakeDataStructureElementBuilder(NAME)
                            .withIndex(null)
                            .build(),
                        AGE
                    ).build()
            )
        );
    }

}