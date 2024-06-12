package com.example.datastructure.domain;

import com.example.TestRandomUtils;
import com.example.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.datastructure.client.dto.RemoveIndexCommandDTO;
import com.example.datastructure.domain.command.CreateDataStructureCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static com.example.datastructure.domain.FakeDataStructureBuilder.fakeDataStructureBuilder;
import static com.example.datastructure.domain.FakeDataStructureElementBuilder.fakeDataStructureElementBuilder;
import static com.example.datastructure.domain.command.FakeCreateCommandDataStructureElementBuilder.fakeCreateCommandDataStructureElementBuilder;
import static com.example.datastructure.domain.command.FakeCreateDataStructureCommandBuilder.fakeCreateDataStructureCommandBuilder;
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

    private static final String DATA_STRUCTURE_NAME = TestRandomUtils.getRandomString(10);
    private static final String INDEX_NAME = TestRandomUtils.getRandomString(10);


    @ParameterizedTest(name = "{0}")
    @MethodSource("successCreateDataStructureParameters")
    void success_create_data_structure(
        String useCase,
        CreateDataStructureCommand command,
        DataStructure expected
    ) {
        //when && then
        DataStructure result = DataStructure.create(command);

        assertThat(result)
            .isEqualTo(expected);
    }

    private static Stream<Arguments> successCreateDataStructureParameters() {
        return Stream.of(
            Arguments.of(
                "Create simple data structure",
                new CreateDataStructureCommand(
                    fakeCreateDataStructureCommandBuilder()
                        .withName("PERSON")
                        .withDataStructureElements(
                            fakeCreateCommandDataStructureElementBuilder()
                                .withName("NAME")
                                .withDataType(DataType.STRING)
                                .withLength(DEFAULT_LENGTH)
                                .withRelatedDataStructure(null)
                                .build(),
                            fakeCreateCommandDataStructureElementBuilder()
                                .withName("AGE")
                                .withDataType(DataType.NUMBER)
                                .withLength(null)
                                .withRelatedDataStructure(null)
                                .build()
                        ).build()
                ),
                fakeDataStructureBuilder()
                    .withName("PERSON")
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder()
                            .withName("NAME")
                            .withDataType(DataType.STRING)
                            .withLength(DEFAULT_LENGTH)
                            .withIndex(null)
                            .withRelatedDataStructure(null)
                            .build(),
                        fakeDataStructureElementBuilder()
                            .withName("AGE")
                            .withDataType(DataType.NUMBER)
                            .withLength(null)
                            .withIndex(null)
                            .withRelatedDataStructure(null)
                            .build()
                    ).build()
            ),
            Arguments.of(
                "Create tree data structure",
                new CreateDataStructureCommand(
                    fakeCreateDataStructureCommandBuilder()
                        .withName("PERSON")
                        .withDataStructureElements(
                            fakeCreateCommandDataStructureElementBuilder()
                                .withName("NAME")
                                .withDataType(DataType.STRING)
                                .withLength(DEFAULT_LENGTH)
                                .withRelatedDataStructure(null)
                                .build(),
                            fakeCreateCommandDataStructureElementBuilder()
                                .withName("ADDRESS_ID")
                                .withDataType(DataType.NUMBER)
                                .withLength(null)
                                .withRelatedDataStructure(
                                    fakeCreateDataStructureCommandBuilder()
                                        .withName("ADDRESS")
                                        .withDataStructureElements(
                                            fakeCreateCommandDataStructureElementBuilder()
                                                .withName("ID")
                                                .withDataType(DataType.NUMBER)
                                                .withLength(null)
                                                .withRelatedDataStructure(null)
                                                .build()
                                        ).build()
                                ).build()
                        ).build()
                ),
                fakeDataStructureBuilder()
                    .withName("PERSON")
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder()
                            .withName("NAME")
                            .withDataType(DataType.STRING)
                            .withLength(DEFAULT_LENGTH)
                            .withIndex(null)
                            .withRelatedDataStructure(null)
                            .build(),
                        fakeDataStructureElementBuilder()
                            .withName("ADDRESS_ID")
                            .withDataType(DataType.NUMBER)
                            .withLength(null)
                            .withIndex(null)
                            .withRelatedDataStructure(
                                fakeDataStructureBuilder()
                                    .withName("ADDRESS")
                                    .withDataStructureElements(
                                        fakeDataStructureElementBuilder()
                                            .withName("ID")
                                            .withDataType(DataType.NUMBER)
                                            .withLength(null)
                                            .withRelatedDataStructure(null)
                                            .build()
                                    ).build()
                            )
                            .build()
                    ).build()
            )
        );
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
                    IndexType.SINGLE
                ),
                fakeDataStructureBuilder()
                    .withName(DATA_STRUCTURE_NAME)
                    .withDataStructureElements(
                        fakeDataStructureElementBuilder(ID)
                            .withIndex(new Index(INDEX_NAME, IndexType.SINGLE))
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
                            .withIndex(new Index(INDEX_NAME, IndexType.SINGLE))
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