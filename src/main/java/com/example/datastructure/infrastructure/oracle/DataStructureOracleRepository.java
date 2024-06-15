package com.example.datastructure.infrastructure.oracle;

import com.example.connection.application.OracleConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DataStructureOracleRepository {

    private final OracleConnection oracleConnection;

    void execute(String script) {
        oracleConnection.getJdbcTemplate().execute(script);
    }

    void query(String script) {
        oracleConnection.getJdbcTemplate().execute(script);
    }
}
