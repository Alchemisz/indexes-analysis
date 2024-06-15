package com.example.connection.application;

import com.example.connection.client.command.SetupConnectionCommand;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SetupMongoDbConnectionServiceTest {

    public static final String LOCALHOST = "localhost";
    public static final int PORT = 27017;
    public static final String DATABASE_NAME = "Testowa";
    public static final String USERNAME = "user";
    public static final String PASSWORD = "secret";
    private final MongoConnection mongoConnection;
    private final SetupMongoDbConnectionService sut;

    public SetupMongoDbConnectionServiceTest() {
        this.mongoConnection = mock(MongoConnection.class);
        this.sut = new SetupMongoDbConnectionService(mongoConnection);
    }

    @ParameterizedTest
    @MethodSource("prepareTestData")
    void setup_connection(SetupConnectionCommand command) {
        //when
        sut.setupConnection(command);

        //then
        ArgumentCaptor<MongoClient> mongoClientArgumentCaptor = ArgumentCaptor.forClass(MongoClient.class);
        ArgumentCaptor<MongoTemplate> mongoTemplateArgumentCaptor = ArgumentCaptor.forClass(MongoTemplate.class);
        verify(mongoConnection)
            .setMongoClient(mongoClientArgumentCaptor.capture());
        verify(mongoConnection)
            .setMongoTemplate(mongoTemplateArgumentCaptor.capture());

        Assertions.assertThat(mongoClientArgumentCaptor.getValue().getDatabase(DATABASE_NAME))
            .isNotNull();
        Assertions.assertThat(mongoTemplateArgumentCaptor.getValue().getDb().getName())
            .isEqualTo(DATABASE_NAME);
    }

    public static Stream<Arguments> prepareTestData() {
        return Stream.of(
            Arguments.of(
                new SetupConnectionCommand(
                    LOCALHOST,
                    PORT,
                    "",
                    "",
                    DATABASE_NAME
                ),
                expectedConnectionString("mongodb://" + LOCALHOST + ":" + PORT + "/" + DATABASE_NAME)
            ),
            Arguments.of(
                new SetupConnectionCommand(
                    LOCALHOST,
                    PORT,
                    USERNAME,
                    PASSWORD,
                    DATABASE_NAME
                ),
                expectedConnectionString("mongodb://" + USERNAME + ":" + PASSWORD + "@" + LOCALHOST + ":" + PORT + "/" + DATABASE_NAME)
            )
        );
    }

    private static ConnectionString expectedConnectionString(String connectionString) {
        return new ConnectionString(connectionString);
    }

}