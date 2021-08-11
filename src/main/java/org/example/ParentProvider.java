package org.example;

public interface ParentProvider<T extends Parent> {
    T provide();
}
