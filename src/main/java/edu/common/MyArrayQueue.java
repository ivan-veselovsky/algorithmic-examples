package edu.common;

import java.util.Objects;
import java.util.function.IntFunction;

// Same as java.util.ArrayDeque (Java 21), written just for training.
public class MyArrayQueue<T> {

    private T[] array;
    private final IntFunction<T[]> arrayCreator;
    private int headInclusive; // position of the 1st
    private int size; // number of elements in the Queue

    public MyArrayQueue(int initialArrayLength, IntFunction<T[]> arrayCreator) {
        this.arrayCreator = Objects.requireNonNull(arrayCreator);
        this.array = arrayCreator.apply(initialArrayLength);
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void enqueueTail(T t) {
        t = Objects.requireNonNull(t);
        if (size == array.length) {
            expandArray();
        }
        int realIndex = realIndex(size);
        array[realIndex] = t;
        size++;
    }

    public T dequeueHead() {
        if (isEmpty()) {
            return null;
        }
        T value = array[headInclusive];
        headInclusive = (headInclusive + 1) % array.length;
        size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return array[headInclusive];
    }

    private void expandArray() {
        assert size == array.length;
        int newLength = array.length << 1;
        if (newLength < array.length) {
            throw new IllegalStateException("Array size range overflow, cannot double size from " + array.length);
        }
        T[] array2 = arrayCreator.apply(newLength);
        assert array2.length == array.length * 2;
        for (int virtualIndex = 0; virtualIndex < size; virtualIndex++) {
            int realIndex = realIndex(virtualIndex);
            array2[virtualIndex] = array[realIndex];
        }
        array = array2;
        headInclusive = 0;
    }

    private int realIndex(int virtualIndex) {
        if (virtualIndex < 0 || virtualIndex >= array.length) {
            throw new IndexOutOfBoundsException("Index " + virtualIndex + " is out of bounds for length " + array.length);
        }
        final int real = (headInclusive + virtualIndex) % array.length;

        assert real >= 0;
        assert real < array.length;
        return real;
    }
}
