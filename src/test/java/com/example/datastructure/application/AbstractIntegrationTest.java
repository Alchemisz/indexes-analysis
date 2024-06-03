package com.example.datastructure.application;

import com.example.datastructure.infrastructure.DataStructureCache;
import com.example.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
import com.example.databaseconfiguration.infrastructure.DatabaseConfigurationRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    protected DataStructureCache dataStructureCache;
    @Autowired
    protected DatabaseConfigurationRepositoryPort databaseConfigurationRepositoryPort;

    protected void dropTableOracle(String dataStructureName) {
        jdbcTemplate.execute(String.format("DROP TABLE %s", dataStructureName));
    }

    protected void dropCollectionMongoDb(String dataStructureName) {
        mongoTemplate.dropCollection(dataStructureName);
    }

    protected void enableOracleDatabase() {
        databaseConfigurationRepositoryPort.update(
            new DatabaseConfigurationDTO(
                true,
                false
            )
        );
    }

    protected void enableMongoDbDatabase() {
        databaseConfigurationRepositoryPort.update(
            new DatabaseConfigurationDTO(
                false,
                true
            )
        );
    }

}
