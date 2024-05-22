package com.example.demo.datastructure.infrastructure.mongodb;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.domain.DataStructureElement;
import com.example.demo.datastructure.infrastructure.CreateIndexParameters;
import com.example.demo.datastructure.infrastructure.DataStructures;
import com.example.demo.datastructure.infrastructure.ExtractSubStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class DataStructureMongoDbRepositoryAdapter implements DataStructureMongoDbRepositoryPort {

    private final DataStructureMongoDbRepository mongoDbRepository;
    private final ExtractSubStructureService extractSubStructureService;
    private final MongoIndexService mongoIndexService;

    @Override
    public void createDataStructure(DataStructure dataStructure) {
        DataStructures dataStructures = extractSubStructureService.extractDataStructures(dataStructure);
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
        boolean isUnique = false;
        if (isUnique) {
            index.unique();
        }
        mongoDbRepository.createIndex(createIndexParameters.dataStructureName(), index);
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
        mongoDbRepository.execute(query);
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
                return JsonSchemaProperty.string(dataStructureElement.getName());
            }
            case BOOLEAN -> {
                return JsonSchemaProperty.bool(dataStructureElement.getName());
            }
            default ->
                throw new IllegalStateException(String.format("Unhandled data type %s", dataStructureElement.getDataType()));
        }
    }

    private static String[] extractFieldNames(DataStructure dataStructure) {
        return dataStructure.dataStructureElements().stream()
            .map(DataStructureElement::getName)
            .toArray(String[]::new);
    }

}
