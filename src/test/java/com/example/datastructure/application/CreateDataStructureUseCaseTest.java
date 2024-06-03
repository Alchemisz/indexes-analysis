package com.example.datastructure.application;

import com.example.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.domain.DataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.datastructure.domain.FakeDataStructureBuilder.fakeDataStructureBuilder;
import static com.example.datastructure.domain.FakeDataStructureElementBuilder.fakeDataStructureElementBuilder;
import static org.assertj.core.api.Assertions.assertThat;

class CreateDataStructureUseCaseTest extends AbstractIntegrationTest {

    private static final String DATA_STRUCTURE_NAME = "PERSON";

    @Autowired
    private CreateDataStructureUseCase sut;

    @Test
    void create_data_structure_oracle() {
        //given
        enableOracleDatabase();

        CreateDataStructureCommandDTO command = new CreateDataStructureCommandDTO(
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
        );

        DataStructure expected = fakeDataStructureBuilder()
            .withName(DATA_STRUCTURE_NAME)
            .withDataStructureElements(
                fakeDataStructureElementBuilder()
                    .withName("ID")
                    .withDataType(DataType.NUMBER)
                    .withLength(null)
                    .withRelatedDataStructure(null)
                    .build(),
                fakeDataStructureElementBuilder()
                    .withName("NAME")
                    .withDataType(DataType.STRING)
                    .withLength(50)
                    .withRelatedDataStructure(null)
                    .build()
            ).build();

        //when
        sut.create(command);

        //then
        DataStructure result = dataStructureCache.findByName(DATA_STRUCTURE_NAME);
        assertThat(result)
            .isEqualTo(expected);

        dropTableOracle(DATA_STRUCTURE_NAME);
    }

    @Test
    void create_data_structure_mongo() {
        //given
        enableMongoDbDatabase();

        CreateDataStructureCommandDTO command = new CreateDataStructureCommandDTO(
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
        );

        DataStructure expected = fakeDataStructureBuilder()
            .withName(DATA_STRUCTURE_NAME)
            .withDataStructureElements(
                fakeDataStructureElementBuilder()
                    .withName("ID")
                    .withDataType(DataType.NUMBER)
                    .withLength(null)
                    .withRelatedDataStructure(null)
                    .build(),
                fakeDataStructureElementBuilder()
                    .withName("NAME")
                    .withDataType(DataType.STRING)
                    .withLength(50)
                    .withRelatedDataStructure(null)
                    .build()
            ).build();

        //when
        sut.create(command);

        //then
        DataStructure result = dataStructureCache.findByName(DATA_STRUCTURE_NAME);
        assertThat(result)
            .isEqualTo(expected);

        dropCollectionMongoDb(DATA_STRUCTURE_NAME);
    }

}