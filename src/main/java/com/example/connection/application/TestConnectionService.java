package com.example.connection.application;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
class TestConnectionService {

    private final MongoConnection mongoConnection;
    private final OracleConnection oracleConnection;


    boolean testMongoDbConnection(String databaseName) {
        if (isNull(mongoConnection.getMongoClient())) {
            return false;
        }

        MongoIterable<String> databasesNames = mongoConnection.getMongoClient().listDatabaseNames();
        MongoCursor<String> databasesNamesIterator = databasesNames.iterator();

        while (databasesNamesIterator.hasNext()) {
            if (Objects.equals(databasesNamesIterator.tryNext(), databaseName)) {
                return true;
            }
        }
        return false;
    }

    boolean testOracleConnection() {
        try {
            String sql = "SELECT 1 FROM DUAL";
            oracleConnection.getJdbcTemplate().queryForObject(sql, Integer.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
