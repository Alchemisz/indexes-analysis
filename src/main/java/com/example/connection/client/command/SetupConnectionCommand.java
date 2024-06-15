package com.example.connection.client.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SetupConnectionCommand {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String database;
}
