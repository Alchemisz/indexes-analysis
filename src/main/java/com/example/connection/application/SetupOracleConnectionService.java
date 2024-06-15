package com.example.connection.application;

import com.example.connection.client.command.SetupConnectionCommand;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
class SetupOracleConnectionService {

    private final OracleConnection oracleConnection;

    void setupConnection(SetupConnectionCommand command) {
        DataSource dataSource = createDataSource(command);
        oracleConnection.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private DataSource createDataSource(SetupConnectionCommand command) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(prepareJdbcUrl(command));
        config.setUsername(command.username());
        config.setPassword(command.password());
        config.setDriverClassName("oracle.jdbc.OracleDriver");
        return new HikariDataSource(config);
    }

    private static String prepareJdbcUrl(SetupConnectionCommand command) {
        return "jdbc:oracle:thin:@//" + command.host() + ":" + command.port() + "/" + command.database();
    }
}
