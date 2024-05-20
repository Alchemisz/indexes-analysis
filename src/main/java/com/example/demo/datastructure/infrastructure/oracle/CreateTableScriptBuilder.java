package com.example.demo.datastructure.infrastructure.oracle;

import java.util.HashSet;
import java.util.Set;

class CreateTableScriptBuilder {

    private final String tableName;
    private final Set<String> fields;

    CreateTableScriptBuilder(String tableName) {
        this.tableName = tableName;
        this.fields = new HashSet<>();
    }

    CreateTableScriptBuilder addField(String field) {
        this.fields.add(field);
        return this;
    }

    String build() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("CREATE TABLE %s", tableName));
        sb.append("(");

        sb.append(String.join(",", fields));

        sb.append(")");
        return sb.toString();
    }

}
