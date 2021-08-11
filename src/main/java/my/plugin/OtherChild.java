package my.plugin;

import org.example.Parent;

public class OtherChild extends Parent {
    private final String otherChildProp;

    public OtherChild(String grandParentProp, String parentProp, String otherChildProp) {
        super(grandParentProp, parentProp);
        this.otherChildProp = otherChildProp;
    }

    public String getOtherChildProp() {
        return otherChildProp;
    }
}
