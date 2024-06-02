package com.example.demo.datastructure.domain;

import com.example.demo.TestRandomUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class FakeDataStructureBuilder {
    private String name;
    private List<DataStructureElement> dataStructureElements;

    private FakeDataStructureBuilder() {
        this.name = TestRandomUtils.getRandomString(10);
        this.dataStructureElements = Collections.emptyList();
    }

    static FakeDataStructureBuilder fakeDataStructureBuilder() {
        return new FakeDataStructureBuilder();
    }

    FakeDataStructureBuilder withName(String name) {
        this.name = name;
        return this;
    }

    FakeDataStructureBuilder withDataStructureElements(DataStructureElement... elements) {
        this.dataStructureElements = Arrays.asList(elements);
        return this;
    }

    DataStructure build() {
        return new DataStructure(
            name,
            dataStructureElements
        );
    }
}

