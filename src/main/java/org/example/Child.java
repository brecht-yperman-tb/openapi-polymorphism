package org.example;

public class Child extends Parent {
    private final String childProp;

    public Child(String grandParentProp, String parentProp, String childProp) {
        super(grandParentProp, parentProp);
        this.childProp = childProp;
    }

    public String getChildProp() {
        return childProp;
    }
}
