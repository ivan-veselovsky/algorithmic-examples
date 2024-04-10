package edu.common;

import java.util.function.Supplier;

public record NodeP<T>(T payload, String name, NodeP<T> left, NodeP<T> right) {
    public static <T> NodeP<T> of(String name, NodeP<T> left, NodeP<T> right) {
        return new NodeP<>(null, name, left, right);
    }

    public static <T> NodeP<T> of(String name) {
        return new NodeP<>(null, name, null, null);
    }

    public static <T> NodeP<T> of(Supplier<T> payloadFactory, String name) {
        return new NodeP<>(payloadFactory.get(), name, null, null);
    }

    public static <T> NodeP<T> of(Supplier<T> payloadFactory, String name, NodeP<T> left, NodeP<T> right) {
        return new NodeP<>(payloadFactory.get(), name, left, right);
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
