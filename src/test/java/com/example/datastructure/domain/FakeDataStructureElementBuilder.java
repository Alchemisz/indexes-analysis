package com.example.datastructure.domain;

import com.example.TestRandomUtils;

public class FakeDataStructureElementBuilder {
    private String name;
    private DataType dataType;
    private Integer length;
    private Index index;
    private DataStructure relatedDataStructure;

    private FakeDataStructureElementBuilder() {
        this.name = TestRandomUtils.getRandomString(10);
        this.dataType = TestRandomUtils.getRandomElementOfArray(DataType.values());
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

    public static FakeDataStructureElementBuilder fakeDataStructureElementBuilder() {
        return new FakeDataStructureElementBuilder();
    }

    public static FakeDataStructureElementBuilder fakeDataStructureElementBuilder(DataStructureElement dataStructureElement) {
        return new FakeDataStructureElementBuilder(dataStructureElement);
    }

    public FakeDataStructureElementBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FakeDataStructureElementBuilder withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public FakeDataStructureElementBuilder withLength(Integer length) {
        this.length = length;
        return this;
    }

    public FakeDataStructureElementBuilder withIndex(Index index) {
        this.index = index;
        return this;
    }

    public FakeDataStructureElementBuilder withRelatedDataStructure(DataStructure relatedDataStructure) {
        this.relatedDataStructure = relatedDataStructure;
        return this;
    }

    public DataStructureElement build() {
        return new DataStructureElement(
            name,
            dataType,
            length,
            index,
            relatedDataStructure
        );
    }
}