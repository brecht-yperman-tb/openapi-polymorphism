package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;

public class GrandParent {
    private final String grandParentProp;

    @JsonCreator
    public GrandParent(String grandParentProp) {
        this.grandParentProp = grandParentProp;
    }

    public String getGrandParentProp() {
        return grandParentProp;
    }
}
