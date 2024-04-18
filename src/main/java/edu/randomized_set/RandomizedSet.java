package edu.randomized_set;

import java.util.HashMap;
import java.util.Random;

class RandomizedSet {
    private int size;
    private int[] vector = new int[16];
    private final HashMap<Integer, Integer> valuesToPositions = new HashMap<>(16);
    private final Random random = new Random(12345L);

    public RandomizedSet() {
    }

    private void increaseVector() {
        int[] vector2 = new int[vector.length << 1];
        System.arraycopy(vector, 0, vector2, 0, vector.length);
        vector = vector2;
    }

    public boolean insert(final int val) {
        Integer old = valuesToPositions.putIfAbsent(val, size);
        if (old != null) {
            return false;
        }
        if (size == vector.length) {
            increaseVector();
        }
        assert size < vector.length;
        vector[size] = val;
        size++;
        return true;
    }

    public boolean remove(int val) {
        final Integer pos = valuesToPositions.remove(val);
        if (pos == null) {
            return false;
        } else {
            assert size > 0;
            if (pos != size - 1) {
                int lastValueInVector = vector[size - 1];
                vector[pos] = lastValueInVector;
                boolean replaced = valuesToPositions.replace(lastValueInVector, size - 1, pos);
                assert replaced;
            }
            size--;
            return true;
        }
    }

    public int getRandom() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomIndex = random.nextInt(0, size);
        return vector[randomIndex];
    }
}
