package com.example.connection.client;

import com.example.connection.application.ConnectionFacade;
import com.example.connection.client.command.SetupConnectionCommand;
import com.example.datastructure.shared.Database;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicesrest/connection")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionFacade connectionFacade;

    @PostMapping("/connect/{database}")
    public void connectToDatabase(@RequestBody SetupConnectionCommand command, @PathVariable Database database) {
        connectionFacade.connectToDatabase(command, database);
    }

    @PostMapping("/test/{databaseName}/{database}")
    public boolean testDatabaseConnection(@PathVariable String databaseName, @PathVariable Database database) {
        return connectionFacade.testDatabaseConnection(databaseName, database);
    }

}
