package com.example.connection.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Setter
@Getter
public class OracleConnection {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
}
