package edu.heap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;

class HeapTest {

    @Test
    void case1() {
        int[] array = new int[] { 9, 7, 4, 4, 1, 0, 7,
                7, 18, 5, 2};
        Heap heap = new Heap(array, 7);
        heap.buildHeap();
        then(heap.size()).isEqualTo(7);

        then(heap.removeMax()).isEqualTo(9);
        then(heap.size()).isEqualTo(6);
        then(heap.removeMax()).isEqualTo(7);
        then(heap.size()).isEqualTo(5);
        then(heap.removeMax()).isEqualTo(7);
        then(heap.size()).isEqualTo(4);
        then(heap.removeMax()).isEqualTo(4);
        then(heap.size()).isEqualTo(3);
        then(heap.removeMax()).isEqualTo(4);
        then(heap.size()).isEqualTo(2);
        then(heap.removeMax()).isEqualTo(1);
        then(heap.size()).isEqualTo(1);
        then(heap.removeMax()).isEqualTo(0);
        then(heap.size()).isEqualTo(0);
    }

    @Test
    void case2() {
        int[] array = new int[] { 9, 7, 4, 4, 1, 0, 7, 7, 18, 5, 2};
        Heap heap = new Heap(array, array.length);
        heap.buildHeap();
        then(heap.size()).isEqualTo(array.length);

        then(heap.removeMax()).isEqualTo(18);
        then(heap.size()).isEqualTo(10);
        then(heap.removeMax()).isEqualTo(9);
        then(heap.size()).isEqualTo(9);
        then(heap.removeMax()).isEqualTo(7);
        then(heap.size()).isEqualTo(8);
        then(heap.removeMax()).isEqualTo(7);
        then(heap.size()).isEqualTo(7);
        then(heap.removeMax()).isEqualTo(7);
        then(heap.size()).isEqualTo(6);
        then(heap.removeMax()).isEqualTo(5);
        then(heap.size()).isEqualTo(5);
        then(heap.removeMax()).isEqualTo(4);
        then(heap.size()).isEqualTo(4);
        then(heap.removeMax()).isEqualTo(4);
        then(heap.size()).isEqualTo(3);
        then(heap.removeMax()).isEqualTo(2);
        then(heap.size()).isEqualTo(2);
        then(heap.removeMax()).isEqualTo(1);
        then(heap.size()).isEqualTo(1);
        then(heap.removeMax()).isEqualTo(0);
        then(heap.size()).isEqualTo(0);
    }

    @Test
    void case3() {
        final int[] array = new int[1517];
        Random random = new Random(12345L);
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(-5000, 5000);
        }

        int[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);

        final Heap heap = new Heap(array, array.length);
        heap.buildHeap();

        for (int i = 0; i < array.length; i++) {
            int next = heap.removeMax();
            int size = heap.size();
            then(next).isEqualTo(expected[array.length - i - 1]);
            then(size).isEqualTo(array.length - i - 1);
        }
    }
}