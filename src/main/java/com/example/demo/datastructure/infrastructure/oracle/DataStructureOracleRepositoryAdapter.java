package com.example.demo.datastructure.infrastructure.oracle;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.domain.DataStructureElement;
import com.example.demo.datastructure.infrastructure.CreateIndexParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
class DataStructureOracleRepositoryAdapter implements DataStructureOracleRepositoryPort {

    private final DataStructureOracleRepository dataStructureOracleRepository;

    @Override
    public void createDataStructure(DataStructure dataStructure) {
        Stack<String> dataStructuresStack = new Stack<>();

        List<DataStructure> dataStructures = new ArrayList<>();
        DataStructure current = dataStructure;

        do {
            current.dataStructureElements().forEach(dataStructureElement -> {
                if (dataStructureElement.getRelatedDataStructure() != null) {
                    dataStructures.add(dataStructureElement.getRelatedDataStructure());
                }
            });
            dataStructures.remove(current);
            dataStructuresStack.add(buildCreateTableScript(current));
            if (!dataStructures.isEmpty()) {
                current = dataStructures.get(dataStructures.size() - 1);
            } else {
                current = null;
            }
        } while (current != null);

        while (!dataStructuresStack.isEmpty()) {
            dataStructureOracleRepository.execute(dataStructuresStack.pop());
        }
    }

    @Override
    public void createIndex(CreateIndexParameters createIndexParameters) {
        CreateIndexScriptBuilder createIndexScriptBuilder = new CreateIndexScriptBuilder(
            createIndexParameters.dataStructureName(),
            createIndexParameters.indexName(),
            createIndexParameters.dataStructureElementNames()
        );

        String script = createIndexScriptBuilder.build();
        dataStructureOracleRepository.execute(script);
    }

    @Override
    public void removeIndex(String indexName) {
        String script = format("DROP INDEX %s", indexName);
        dataStructureOracleRepository.execute(script);
    }

    @Override
    public void executeQuery(String query) {
        dataStructureOracleRepository.query(query);
    }

    private String buildCreateTableScript(DataStructure current) {
        CreateTableScriptBuilder createTableScriptBuilder = new CreateTableScriptBuilder(current.name());
        current.dataStructureElements().forEach(entry -> createTableScriptBuilder.addField(buildField(entry)));
        return createTableScriptBuilder.build();
    }

    private static String buildField(DataStructureElement entry) {
        var dataType = getDataType(entry);
        return format("%s %s", entry.getName(), dataType);
    }

    private static String getDataType(DataStructureElement dataStructureElement) {
        return switch (dataStructureElement.getDataType()) {
            case NUMBER -> "NUMBER";
            case STRING -> format("VARCHAR2(%s)", dataStructureElement.getLength());
            case BOOLEAN -> "VARCHAR2(5)";
            default -> throw new IllegalStateException(format("Unhandled data type: %s", dataStructureElement));
        };
    }
}
