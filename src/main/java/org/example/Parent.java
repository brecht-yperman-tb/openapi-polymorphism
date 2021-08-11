package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "child", value = Child.class)
})
public class Parent extends GrandParent {
    private final String parentProp;

    public Parent(String grandParentProp, String parentProp) {
        super(grandParentProp);
        this.parentProp = parentProp;
    }

    public String getParentProp() {
        return parentProp;
    }
}
