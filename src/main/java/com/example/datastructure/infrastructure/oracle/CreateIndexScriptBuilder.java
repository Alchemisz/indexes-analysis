package com.example.datastructure.infrastructure.oracle;

import com.example.datastructure.domain.IndexType;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
class CreateIndexScriptBuilder {

    private final String tableName;
    private final String indexName;
    private final IndexType indexType;
    private final Set<String> fields;


    String build() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE ");

        if (indexType == IndexType.UNIQUE) {
            sb.append(" UNIQUE ");
        }
        sb.append(String.format("INDEX %s ON %s(%s)", indexName, tableName, String.join(",", fields)));

        return sb.toString();
    }

}
