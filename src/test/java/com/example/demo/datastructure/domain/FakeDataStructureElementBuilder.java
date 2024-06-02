package com.example.demo.datastructure.domain;

import static com.example.demo.TestRandomUtils.getRandomElementOfArray;
import static com.example.demo.TestRandomUtils.getRandomString;

class FakeDataStructureElementBuilder {
    private String name;
    private DataType dataType;
    private Integer length;
    private Index index;
    private DataStructure relatedDataStructure;

    private FakeDataStructureElementBuilder() {
        this.name = getRandomString(10);
        this.dataType = getRandomElementOfArray(DataType.values());
        this.length = 10;
        this.index = null;
        this.relatedDataStructure = null;
    }

    public FakeDataStructureElementBuilder(DataStructureElement dataStructureElement) {
        this.name = dataStructureElement.getName();
        this.dataType = dataStructureElement.getDataType();
        this.length = dataStructureElement.getLength();
        this.index = dataStructureElement.getIndex();
        this.relatedDataStructure = dataStructureElement.getRelatedDataStructure();
    }

    static FakeDataStructureElementBuilder fakeDataStructureElementBuilder() {
        return new FakeDataStructureElementBuilder();
    }

    static FakeDataStructureElementBuilder fakeDataStructureElementBuilder(DataStructureElement dataStructureElement) {
        return new FakeDataStructureElementBuilder(dataStructureElement);
    }

    FakeDataStructureElementBuilder withName(String name) {
        this.name = name;
        return this;
    }

    FakeDataStructureElementBuilder withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    FakeDataStructureElementBuilder withLength(Integer length) {
        this.length = length;
        return this;
    }

    FakeDataStructureElementBuilder withIndex(Index index) {
        this.index = index;
        return this;
    }

    FakeDataStructureElementBuilder withRelatedDataStructure(DataStructure relatedDataStructure) {
        this.relatedDataStructure = relatedDataStructure;
        return this;
    }

    DataStructureElement build() {
        return new DataStructureElement(
            name,
            dataType,
            length,
            index,
            relatedDataStructure
        );
    }
}