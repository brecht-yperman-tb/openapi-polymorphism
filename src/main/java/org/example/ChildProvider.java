package org.example;

import org.springframework.stereotype.Component;

@Component
public class ChildProvider implements ParentProvider<Child> {
    @Override
    public Child provide() {
        return new Child("gramps", "dad", "sis");
    }
}
