package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.domain.DataStructureElement;
import com.example.demo.datastructure.domain.DataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DataStructureOracleRepositoryAdapterTest {

    private final DataStructureOracleRepositoryAdapter sut;

    @Autowired
    public DataStructureOracleRepositoryAdapterTest(DataStructureOracleRepositoryAdapter dataStructureOracleRepositoryAdapter) {
        this.sut = dataStructureOracleRepositoryAdapter;
    }

    @Test
    void create_data_structure() {
        var dataStructure = new DataStructure(
            "Person",
            List.of(
                new DataStructureElement("Address_Id", DataType.STRING,
                    new DataStructure(
                        "Address",
                        List.of(
                            new DataStructureElement("id", DataType.NUMBER, null),
                            new DataStructureElement("city_id", DataType.NUMBER,
                                new DataStructure(
                                    "City",
                                    List.of(
                                        new DataStructureElement("id", DataType.NUMBER, null),
                                        new DataStructureElement("name", DataType.STRING, null)
                                    )
                                )
                            )
                        )
                    )
                ),
                new DataStructureElement("Age", DataType.NUMBER, null)
            )
        );

        sut.createDataStructure(dataStructure);
    }

}