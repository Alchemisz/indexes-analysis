package com.example.datastructure.domain.command;

import com.example.TestRandomUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.datastructure.domain.command.CreateDataStructureCommand.DataStructure;
import static com.example.datastructure.domain.command.CreateDataStructureCommand.DataStructureElement;

public class FakeCreateDataStructureCommandBuilder {

    private String name;
    private List<DataStructureElement> dataStructureElements;

    private FakeCreateDataStructureCommandBuilder() {
        this.name = TestRandomUtils.getRandomString(10);
        this.dataStructureElements = Collections.emptyList();
    }

    public static FakeCreateDataStructureCommandBuilder fakeCreateDataStructureCommandBuilder() {
        return new FakeCreateDataStructureCommandBuilder();
    }

    public FakeCreateDataStructureCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FakeCreateDataStructureCommandBuilder withDataStructureElements(DataStructureElement... elements) {
        this.dataStructureElements = Arrays.asList(elements);
        return this;
    }

    public DataStructure build() {
        return new DataStructure(
            name,
            dataStructureElements
        );
    }

}
