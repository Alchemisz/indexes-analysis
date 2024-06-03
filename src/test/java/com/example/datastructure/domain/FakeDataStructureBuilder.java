package com.example.datastructure.domain;

import com.example.TestRandomUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FakeDataStructureBuilder {
    private String name;
    private List<DataStructureElement> dataStructureElements;

    private FakeDataStructureBuilder() {
        this.name = TestRandomUtils.getRandomString(10);
        this.dataStructureElements = Collections.emptyList();
    }

    public static FakeDataStructureBuilder fakeDataStructureBuilder() {
        return new FakeDataStructureBuilder();
    }

    public FakeDataStructureBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FakeDataStructureBuilder withDataStructureElements(DataStructureElement... elements) {
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

