package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.domain.DataType;
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

    private String buildCreateTableScript(DataStructure current) {
        CreateTableScriptBuilder createTableScriptBuilder = new CreateTableScriptBuilder(current.name());
        current.dataStructureElements().forEach(entry -> createTableScriptBuilder.addField(
            String.format("%s %s", entry.getName(), entry.getDataType() == DataType.NUMBER ? "NUMBER" : "VARCHAR2(255)")
        ));
        return createTableScriptBuilder.build();
    }
}
