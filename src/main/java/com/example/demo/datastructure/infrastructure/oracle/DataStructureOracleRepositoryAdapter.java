package com.example.demo.datastructure.infrastructure.oracle;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.domain.DataType;
import com.example.demo.datastructure.infrastructure.CreateIndexParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        String script = String.format("DROP INDEX %s", indexName);
        dataStructureOracleRepository.execute(script);
    }

    @Override
    public void execute(String query) {
        dataStructureOracleRepository.execute(query);
    }

    private String buildCreateTableScript(DataStructure current) {
        CreateTableScriptBuilder createTableScriptBuilder = new CreateTableScriptBuilder(current.name());
        current.dataStructureElements().forEach(entry -> createTableScriptBuilder.addField(
            String.format("%s %s", entry.getName(), entry.getDataType() == DataType.NUMBER ? "NUMBER" : "VARCHAR2(255)")
        ));
        return createTableScriptBuilder.build();
    }
}
