package com.example.datastructure.domain.command;

import com.example.TestRandomUtils;
import com.example.datastructure.domain.DataType;

import static com.example.datastructure.domain.command.CreateDataStructureCommand.DataStructure;
import static com.example.datastructure.domain.command.CreateDataStructureCommand.DataStructureElement;


public class FakeCreateCommandDataStructureElementBuilder {

    private String name;
    private DataType dataType;
    private Integer length;
    private DataStructure relatedDataStructure;

    private FakeCreateCommandDataStructureElementBuilder() {
        this.name = TestRandomUtils.getRandomString(10);
        this.dataType = TestRandomUtils.getRandomElementOfArray(DataType.values());
        this.length = 10;
        this.relatedDataStructure = null;
    }

    public static FakeCreateCommandDataStructureElementBuilder fakeCreateCommandDataStructureElementBuilder() {
        return new FakeCreateCommandDataStructureElementBuilder();
    }


    public FakeCreateCommandDataStructureElementBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FakeCreateCommandDataStructureElementBuilder withDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public FakeCreateCommandDataStructureElementBuilder withLength(Integer length) {
        this.length = length;
        return this;
    }

    public FakeCreateCommandDataStructureElementBuilder withRelatedDataStructure(DataStructure relatedDataStructure) {
        this.relatedDataStructure = relatedDataStructure;
        return this;
    }

    public DataStructureElement build() {
        return new DataStructureElement(
            name,
            dataType,
            length,
            relatedDataStructure
        );
    }

}
