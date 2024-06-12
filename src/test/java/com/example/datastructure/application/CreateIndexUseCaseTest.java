package com.example.datastructure.application;

import com.example.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.datastructure.domain.*;
import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.domain.DataType;
import com.example.datastructure.domain.Index;
import com.example.datastructure.domain.IndexType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static com.example.datastructure.domain.FakeDataStructureElementBuilder.fakeDataStructureElementBuilder;
import static org.assertj.core.api.Assertions.assertThat;


class CreateIndexUseCaseTest extends AbstractIntegrationTest {

    private static final String DATA_STRUCTURE_NAME = "PERSON";

    @Autowired
    private CreateDataStructureUseCase createDataStructureUseCase;
    @Autowired
    private CreateIndexUseCase sut;

    @Test
    void create_index_oracle() {
        //given
        enableOracleDatabase();

        createDataStructureUseCase.create(
            new CreateDataStructureCommandDTO(
                new CreateDataStructureCommandDTO.DataStructureDTO(
                    DATA_STRUCTURE_NAME,
                    List.of(
                        new CreateDataStructureCommandDTO.DataStructureElementDTO(
                            "ID",
                            DataType.NUMBER,
                            null,
                            null
                        ),
                        new CreateDataStructureCommandDTO.DataStructureElementDTO(
                            "NAME",
                            DataType.STRING,
                            50,
                            null
                        )
                    )
                ),
                true
            )
        );

        DataStructure expected = FakeDataStructureBuilder.fakeDataStructureBuilder()
            .withName(DATA_STRUCTURE_NAME)
            .withDataStructureElements(
                FakeDataStructureElementBuilder.fakeDataStructureElementBuilder()
                    .withName("ID")
                    .withDataType(DataType.NUMBER)
                    .withLength(null)
                    .withIndex(new Index("TEST_IDX", IndexType.SINGLE))
                    .withRelatedDataStructure(null)
                    .build(),
                FakeDataStructureElementBuilder.fakeDataStructureElementBuilder()
                    .withName("NAME")
                    .withDataType(DataType.STRING)
                    .withLength(50)
                    .withRelatedDataStructure(null)
                    .build()
            ).build();

        CreateIndexCommandDTO command = new CreateIndexCommandDTO(
            DATA_STRUCTURE_NAME,
            Set.of("ID"),
            "TEST_IDX",
            IndexType.SINGLE
        );

        //when
        sut.createForDataStructure(command);

        //then
        DataStructure result = dataStructureCache.findByName(DATA_STRUCTURE_NAME);
        assertThat(result)
            .isEqualTo(expected);

        dropTableOracle(DATA_STRUCTURE_NAME);
    }

    @Test
    void create_index_mongo() {
        //given
        enableMongoDbDatabase();

        createDataStructureUseCase.create(
            new CreateDataStructureCommandDTO(
                new CreateDataStructureCommandDTO.DataStructureDTO(
                    DATA_STRUCTURE_NAME,
                    List.of(
                        new CreateDataStructureCommandDTO.DataStructureElementDTO(
                            "ID",
                            DataType.NUMBER,
                            null,
                            null
                        ),
                        new CreateDataStructureCommandDTO.DataStructureElementDTO(
                            "NAME",
                            DataType.STRING,
                            50,
                            null
                        )
                    )
                ),
                true
            )
        );

        DataStructure expected = FakeDataStructureBuilder.fakeDataStructureBuilder()
            .withName(DATA_STRUCTURE_NAME)
            .withDataStructureElements(
                FakeDataStructureElementBuilder.fakeDataStructureElementBuilder()
                    .withName("ID")
                    .withDataType(DataType.NUMBER)
                    .withLength(null)
                    .withIndex(new Index("TEST_IDX", IndexType.SINGLE))
                    .withRelatedDataStructure(null)
                    .build(),
                FakeDataStructureElementBuilder.fakeDataStructureElementBuilder()
                    .withName("NAME")
                    .withDataType(DataType.STRING)
                    .withLength(50)
                    .withRelatedDataStructure(null)
                    .build()
            ).build();

        CreateIndexCommandDTO command = new CreateIndexCommandDTO(
            DATA_STRUCTURE_NAME,
            Set.of("ID"),
            "TEST_IDX",
            IndexType.SINGLE
        );

        //when
        sut.createForDataStructure(command);

        //then
        DataStructure result = dataStructureCache.findByName(DATA_STRUCTURE_NAME);
        assertThat(result)
            .isEqualTo(expected);

        dropCollectionMongoDB(DATA_STRUCTURE_NAME);
    }

}