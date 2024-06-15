package com.example.connection.application;

import com.example.connection.client.command.SetupConnectionCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SetupOracleConnectionServiceTest {

    public static final String LOCALHOST = "localhost";
    public static final int PORT = 1521;
    public static final String DATABASE_NAME = "xe";
    public static final String USERNAME = "adrian";
    public static final String PASSWORD = "adrian";
    private final OracleConnection oracleConnection;
    private final SetupOracleConnectionService sut;

    public SetupOracleConnectionServiceTest() {
        this.oracleConnection = mock(OracleConnection.class);
        this.sut = new SetupOracleConnectionService(oracleConnection);
    }

    @Test
    void setup_connection() {
        //given
        SetupConnectionCommand command = new SetupConnectionCommand(
            LOCALHOST,
            PORT,
            USERNAME,
            PASSWORD,
            DATABASE_NAME
        );

        //when
        sut.setupConnection(command);

        //then
        ArgumentCaptor<JdbcTemplate> jdbcTemplateArgumentCaptor = ArgumentCaptor.forClass(JdbcTemplate.class);
        verify(oracleConnection)
            .setJdbcTemplate(jdbcTemplateArgumentCaptor.capture());

        Assertions.assertThat(jdbcTemplateArgumentCaptor.getValue())
            .isNotNull();
    }

}