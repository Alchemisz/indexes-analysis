package com.example.connection.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestConnectionServiceTest {

    private static final String DATABASE_NAME = "Testowa";

    private final MongoConnection mongoConnection;
    private final OracleConnection oracleConnection;
    private final TestConnectionService sut;

    public TestConnectionServiceTest() {
        this.mongoConnection = mock(MongoConnection.class);
        this.oracleConnection = mock(OracleConnection.class);
        this.sut = new TestConnectionService(mongoConnection, oracleConnection);
    }

    @Test
    void test_mongo_db_connection_is_not_set() {
        //given
        when(mongoConnection.getMongoClient())
            .thenReturn(null);

        //when
        boolean result = sut.testMongoDbConnection(DATABASE_NAME);
        //then
        Assertions.assertThat(result)
            .isFalse();
    }

    @Test
    void test_oracle_connection_is_not_set() {
        //given
        when(oracleConnection.getJdbcTemplate())
            .thenReturn(null);

        //when
        boolean result = sut.testOracleConnection();
        //then
        Assertions.assertThat(result)
            .isFalse();
    }
}