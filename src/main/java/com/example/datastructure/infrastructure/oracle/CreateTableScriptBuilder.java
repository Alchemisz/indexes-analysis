package com.example.datastructure.infrastructure.oracle;

import java.util.ArrayList;
import java.util.List;

class CreateTableScriptBuilder {

    private final String tableName;
    private final List<String> fields;

    CreateTableScriptBuilder(String tableName) {
        this.tableName = tableName;
        this.fields = new ArrayList<>();
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
