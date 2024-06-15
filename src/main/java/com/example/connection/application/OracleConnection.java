package com.example.connection.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class OracleConnection {
    private JdbcTemplate jdbcTemplate;
}
