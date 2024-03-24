package edu.common;

import java.util.function.Supplier;

public record Node<T>(T payload, String name, Node<T> left, Node<T> right) {
    public static <T> Node<T> of(String name, Node<T> left, Node<T> right) {
        return new Node<>(null, name, left, right);
    }

    public static <T> Node<T> of(String name) {
        return new Node<>(null, name, null, null);
    }

    public static <T> Node<T> of(Supplier<T> payloadFactory, String name) {
        return new Node<>(payloadFactory.get(), name, null, null);
    }

    public static <T> Node<T> of(Supplier<T> payloadFactory, String name, Node<T> left, Node<T> right) {
        return new Node<>(payloadFactory.get(), name, left, right);
    }

    public boolean isLeaf() {
        return (left() == null) && (right() == null);
    }

    @Override
    public String toString() {
        if (payload != null) {
            return name + " - " + payload;
        }
        return name;
    }
}
