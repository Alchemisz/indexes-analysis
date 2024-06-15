package com.example.datastructure.infrastructure.oracle;

import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.domain.DataStructureElement;
import com.example.datastructure.infrastructure.CreateIndexParameters;
import com.example.process.domain.Process;
import com.example.process.domain.ProcessType;
import com.example.process.infrastructure.ProcessRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
class OracleDataStructureRepositoryAdapter implements OracleDataStructureRepositoryPort {

    private final DataStructureOracleRepository dataStructureOracleRepository;
    private final ProcessRepositoryPort processRepositoryPort;

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
            createIndexParameters.indexType(),
            createIndexParameters.dataStructureElementNames()
        );

        String script = createIndexScriptBuilder.build();

        Process process = Process.create(ProcessType.INDEX_CREATION, script);
        processRepositoryPort.save(process);

        dataStructureOracleRepository.execute(script);

        process.finish();
        processRepositoryPort.save(process);
    }

    @Override
    public void removeIndex(String indexName) {
        String script = format("DROP INDEX %s", indexName);
        dataStructureOracleRepository.execute(script);
    }

    @Override
    public void executeQuery(String query) {
        Process process = Process.create(ProcessType.QUERY_EXECUTION, query);
        processRepositoryPort.save(process);

        dataStructureOracleRepository.query(query);

        process.finish();
        processRepositoryPort.save(process);
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
