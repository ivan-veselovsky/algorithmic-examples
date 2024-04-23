package edu.heap;

import com.google.common.base.Preconditions;

public class Heap {
    private final int[] data;
    int length;
    public Heap(int[] array, int length){
        this.data = array;
        this.length = length;
    }
    public int size() {
        return length;
    }
    public void buildHeap() {
        int startIndex = parent(length);
        assert startIndex <= length;
        for (int i=startIndex; i >= 1; i--) {
            heapifyFrom(i);
        }
        assert validateHeap();
    }
    private boolean validateHeap() {
        for (int i=length; i >= 1; i--) {
            validate(i);
        }
        return true;
    }
    private void validate(final int i) {
        assert i <= length;
        final int val = get(i);

        final int r = right(i);
        assert parent(r) == i;
        if (r <= length) {
            int rightVal = get(r);
            Preconditions.checkState(val >= rightVal, "Index " + i + ": parent = " + val + ", right = " + rightVal + ", left = " + get(left(i)));
        }

        final int l = left(i);
        assert parent(l) == i;
        if (l <= length) {
            int leftVal = get(l);
            Preconditions.checkState(val >= leftVal, "Index " + i + ": parent = " + val + ", left = " + leftVal + ", right = " + get(right(i)));
        }
    }

    public int removeMax() {
        if (length == 0) {
            throw new RuntimeException("Heap is empty.");
        }
        int maxValue = get(1);
        set(1, get(length));
        length--;
        if (length > 0) {
            heapifyFrom(1);
            assert validateHeap();
        }
        return maxValue;
    }
    private void heapifyFrom(final int i) {
        assert i >= 1 && i <= length;
        int index = i;
        while (index != 0) {
            index = heap3(index, false);
        }
    }
    /** Returns the changed mode index, or 0 otherwise. */
    private int heap3(int i, boolean build) {
        assert i >= 1;
        assert i <= length;

        final int leftIndex = left(i);
        final int rightIndex = right(i);
        if (build) {
            assert leftIndex <= length;
            assert rightIndex <= length;
        }

        if (leftIndex <= length) {
            final int iv = get(i);
            if (rightIndex <= length) {
                final int lv = get(leftIndex);
                final int rv = get(rightIndex);
                if (lv > rv) {
                    if (lv > iv) {
                        swap(i, leftIndex);
                        return leftIndex;
                    }
                } else {
                    if (rv > iv) {
                        swap(i, rightIndex);
                        return rightIndex;
                    }
                }
            } else {
                final int lv = get(leftIndex);
                if (lv > iv) {
                    swap(i, leftIndex);
                    return leftIndex;
                }
            }
        }
        return 0;
    }
    private int get(int i) {
        return data[i - 1];
    }
    private void set(int i, int v) {
        data[i - 1] = v;
    }
    private void swap(int i, int j) {
        int iValue = get(i);
        set(i, get(j));
        set(j, iValue);
    }
    private int left(int i) {
        assert i > 0;
        return i << 1;
    }
    private int right(int i) {
        assert i > 0;
        return (i << 1) + 1;
    }
    private int parent(int i) {
        assert i > 0;
        return i >> 1;
    }
}
