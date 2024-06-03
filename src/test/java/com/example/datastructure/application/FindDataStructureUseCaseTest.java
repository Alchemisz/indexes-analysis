package com.example.datastructure.application;

import com.example.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
import com.example.datastructure.client.dto.DataStructureDTO;
import com.example.datastructure.domain.DataType;
import com.example.datastructure.domain.FakeDataStructureBuilder;
import com.example.datastructure.domain.FakeDataStructureElementBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.datastructure.domain.FakeDataStructureElementBuilder.fakeDataStructureElementBuilder;
import static org.assertj.core.api.Assertions.assertThat;

class FindDataStructureUseCaseTest extends AbstractIntegrationTest {

    private static final String DATA_STRUCTURE_NAME = "PERSON";

    @Autowired
    private FindDataStructureUseCase sut;

    @Test
    void find_data_structure() {
        //given
        databaseConfigurationRepositoryPort.update(
            new DatabaseConfigurationDTO(
                true,
                false
            )
        );

        dataStructureCache.save(
            FakeDataStructureBuilder.fakeDataStructureBuilder()
                .withName(DATA_STRUCTURE_NAME)
                .withDataStructureElements(
                    FakeDataStructureElementBuilder.fakeDataStructureElementBuilder()
                        .withName("ID")
                        .withDataType(DataType.NUMBER)
                        .withLength(null)
                        .withRelatedDataStructure(null)
                        .build(),
                    FakeDataStructureElementBuilder.fakeDataStructureElementBuilder()
                        .withName("NAME")
                        .withDataType(DataType.STRING)
                        .withLength(50)
                        .withRelatedDataStructure(null)
                        .build()
                ).build()
        );

        var expected = new DataStructureDTO(
            DATA_STRUCTURE_NAME,
            List.of(
                new DataStructureDTO.DataStructureElementDTO(
                    "ID",
                    DataType.NUMBER,
                    null,
                    null
                ),
                new DataStructureDTO.DataStructureElementDTO(
                    "NAME",
                    DataType.STRING,
                    null,
                    null
                )
            )
        );

        //when
        var result = sut.findByName(DATA_STRUCTURE_NAME);

        //then
        Assertions.assertThat(result)
            .isEqualTo(expected);
    }

}