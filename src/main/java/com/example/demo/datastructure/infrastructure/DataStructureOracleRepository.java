package com.example.demo.datastructure.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DataStructureOracleRepository {

    private final JdbcTemplate jdbcTemplate;

    void execute(String script) {
        jdbcTemplate.execute(script);
    }
}
