package com.example.demo.datastructure.infrastructure;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class CreateIndexScriptBuilder {

    private final String tableName;
    private final String indexName;
    private final Set<String> fields;


    String build() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("CREATE INDEX %s ON %s(%s)", indexName, tableName, String.join(",", fields)));

        return sb.toString();
    }

}
