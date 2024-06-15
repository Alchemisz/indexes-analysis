package com.example.datastructure.infrastructure.mongodb;

import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.domain.DataStructureElement;
import com.example.datastructure.domain.IndexType;
import com.example.datastructure.infrastructure.CreateIndexParameters;
import com.example.datastructure.infrastructure.DataStructures;
import com.example.datastructure.infrastructure.ExtractStructuresService;
import com.example.process.domain.Process;
import com.example.process.domain.ProcessType;
import com.example.process.infrastructure.ProcessRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.springframework.data.mongodb.core.schema.IdentifiableJsonSchemaProperty.StringJsonSchemaProperty;

@Service
@RequiredArgsConstructor
class MongoDbDataStructureRepositoryAdapter implements MongoDbDataStructureRepositoryPort {

    private final DataStructureMongoDbRepository mongoDbRepository;
    private final ExtractStructuresService extractStructuresService;
    private final ProcessRepositoryPort processRepositoryPort;
    private final MongoIndexService mongoIndexService;

    @Override
    public void createDataStructure(DataStructure dataStructure) {
        DataStructures dataStructures = extractStructuresService.extractDataStructures(dataStructure);
        dataStructures.getAllDataStructures().forEach(entry -> {
            var mongoJsonSchema = this.buildJsonSchema(entry);
            mongoDbRepository.createCollection(entry.name(), mongoJsonSchema);
        });
    }

    @Override
    public void createIndex(CreateIndexParameters createIndexParameters) {
        var index = new Index();
        createIndexParameters.dataStructureElementNames().forEach(fieldName -> index.on(fieldName, Sort.Direction.ASC));

        if (StringUtils.hasText(createIndexParameters.indexName())) {
            index.named(createIndexParameters.indexName());
        }

        if (createIndexParameters.indexType().equals(IndexType.UNIQUE)) {
            index.unique();
        }

        Process process = Process.create(ProcessType.INDEX_CREATION, String.format("Create index mongoDB: %s", createIndexParameters.indexName()));
        processRepositoryPort.save(process);

        mongoDbRepository.createIndex(createIndexParameters.dataStructureName(), index);

        process.finish();
        processRepositoryPort.save(process);
    }

    @Override
    public void removeIndex(String indexName) {
        Optional<String> collectionName = mongoIndexService.findCollectionByIndexNameIndex(indexName);

        if (collectionName.isEmpty()) {
            throw new IllegalStateException(String.format("Can't find collection with index name: %s", indexName));
        }

        mongoDbRepository.removeIndex(collectionName.get(), indexName);
    }

    @Override
    public void execute(String query) {
        Process process = Process.create(ProcessType.QUERY_EXECUTION, query);
        processRepositoryPort.save(process);

        try {
            mongoDbRepository.execute(query);
        }catch (Exception ex){
            processRepositoryPort.deleteById(process.getId());
            throw new IllegalStateException(ex);
        }

        process.finish();
        processRepositoryPort.save(process);
    }

    private MongoJsonSchema buildJsonSchema(DataStructure dataStructure) {
        return MongoJsonSchema.builder()
            .required(extractFieldNames(dataStructure))
            .properties(buildFieldsProperties(dataStructure))
            .build();
    }

    private JsonSchemaProperty[] buildFieldsProperties(DataStructure dataStructure) {
        return dataStructure.dataStructureElements().stream()
            .map(this::buildFieldProperty)
            .toArray(JsonSchemaProperty[]::new);
    }

    private JsonSchemaProperty buildFieldProperty(DataStructureElement dataStructureElement) {
        switch (dataStructureElement.getDataType()) {
            case NUMBER -> {
                return JsonSchemaProperty.number(dataStructureElement.getName());
            }
            case STRING -> {
                return buildStringProperty(dataStructureElement);
            }
            case BOOLEAN -> {
                return JsonSchemaProperty.bool(dataStructureElement.getName());
            }
            default ->
                throw new IllegalStateException(String.format("Unhandled data type %s", dataStructureElement.getDataType()));
        }
    }

    private static StringJsonSchemaProperty buildStringProperty(DataStructureElement dataStructureElement) {
        StringJsonSchemaProperty stringJsonSchemaProperty = JsonSchemaProperty.string(dataStructureElement.getName());
        stringJsonSchemaProperty.maxLength(dataStructureElement.getLength());
        return stringJsonSchemaProperty;
    }

    private static String[] extractFieldNames(DataStructure dataStructure) {
        return dataStructure.dataStructureElements().stream()
            .map(DataStructureElement::getName)
            .toArray(String[]::new);
    }

}
