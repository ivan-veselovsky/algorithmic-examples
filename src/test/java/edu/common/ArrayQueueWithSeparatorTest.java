package edu.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ArrayQueueWithSeparatorTest {

    @Test
    void szie_of_empty() {
        ArrayQueueWithSeparator<Integer> queue = new ArrayQueueWithSeparator<>(1, Integer[]::new);
        then(queue.size()).isZero();
    }

    @Test
    void basic_storage_one_el() {
        ArrayQueueWithSeparator<Integer> queue = new ArrayQueueWithSeparator<>(1, Integer[]::new);
        queue.enqueueTail(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeueHead()).isEqualTo(51);
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_two_el() {
        ArrayQueueWithSeparator<Integer> queue = new ArrayQueueWithSeparator<>(2, Integer[]::new);
        queue.enqueueTail(51);
        queue.enqueueTail(52);
        then(queue.size()).isEqualTo(2);
        then(queue.dequeueHead()).isEqualTo(51);
        then(queue.dequeueHead()).isEqualTo(52);
        then(queue.size()).isEqualTo(0);
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void basic_storage_resize() {
        ArrayQueueWithSeparator<Integer> queue = new ArrayQueueWithSeparator<>(1, Integer[]::new);
        queue.enqueueTail(51);
        queue.enqueueTail(52);
        then(queue.size()).isEqualTo(2);
        then(queue.dequeueHead()).isEqualTo(51);
        then(queue.dequeueHead()).isEqualTo(52);
        then(queue.size()).isEqualTo(0);
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void basic_storage_2_resizes() {
        ArrayQueueWithSeparator<Integer> queue = new ArrayQueueWithSeparator<>(1, Integer[]::new);
        queue.enqueueTail(51);
        then(queue.size()).isEqualTo(1);
        queue.enqueueTail(52);
        then(queue.size()).isEqualTo(2);
        queue.enqueueTail(53);
        then(queue.size()).isEqualTo(3);
        then(queue.dequeueHead()).isEqualTo(51);
        then(queue.size()).isEqualTo(2);
        then(queue.dequeueHead()).isEqualTo(52);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeueHead()).isEqualTo(53);
        then(queue.size()).isEqualTo(0);
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void basic_storage_2_resizes_peek() {
        ArrayQueueWithSeparator<Integer> queue = new ArrayQueueWithSeparator<>(1, Integer[]::new);
        queue.enqueueTail(51);
        then(queue.peek()).isEqualTo(51);
        queue.enqueueTail(52);
        then(queue.peek()).isEqualTo(51);
        queue.enqueueTail(53);
        then(queue.peek()).isEqualTo(51);
        then(queue.dequeueHead()).isEqualTo(51);
        then(queue.peek()).isEqualTo(52);
        then(queue.dequeueHead()).isEqualTo(52);
        then(queue.peek()).isEqualTo(53);
        then(queue.dequeueHead()).isEqualTo(53);
        then(queue.peek()).isNull();
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void separator_1() {
        ArrayQueueWithSeparator<String> queue = new ArrayQueueWithSeparator<>(2, String[]::new);
        then(queue.size()).isEqualTo(0);
        then(queue.markedPosition()).isEqualTo(0);  // *          |

        queue.enqueueTail("a");
        queue.enqueueTail("b");
        queue.enqueueTail("c");
        then(queue.size()).isEqualTo(3);
        then(queue.markedPosition()).isEqualTo(0);  // a* b c      |abc

        then(queue.dequeueHead()).isEqualTo("a");
        then(queue.size()).isEqualTo(2);
        then(queue.markedPosition()).isEqualTo(2); // b c *        bc|

        queue.enqueueTail("d");
        then(queue.size()).isEqualTo(3);
        then(queue.markedPosition()).isEqualTo(2); // b c d*       bc|d

        queue.enqueueTail("e");
        then(queue.size()).isEqualTo(4);
        then(queue.markedPosition()).isEqualTo(2); // b c d* e     bc|de

        then(queue.dequeueHead()).isEqualTo("b");
        then(queue.markedPosition()).isEqualTo(1); // c d* e       cd|e

        then(queue.dequeueHead()).isEqualTo("c");
        then(queue.markedPosition()).isEqualTo(0); // d* e         d|e

        then(queue.dequeueHead()).isEqualTo("d");
        then(queue.markedPosition()).isEqualTo(1); // e *          e|

        then(queue.dequeueHead()).isEqualTo("e");
        then(queue.markedPosition()).isEqualTo(0); // *             |

        then(queue.isEmpty()).isTrue();
    }

    @Test
    void caterpillar() {
        ArrayQueueWithSeparator<String> queue = new ArrayQueueWithSeparator<>(7, String[]::new);

        for (int i = 0; i < 35; i++) {
            queue.enqueueTail("a");
            queue.enqueueTail("b");
            queue.enqueueTail("c");
            then(queue.size()).isEqualTo(3);
            then(queue.markedPosition()).isEqualTo(0);

            then(queue.dequeueHead()).isEqualTo("a");
            then(queue.markedPosition()).isEqualTo(2);
            then(queue.dequeueHead()).isEqualTo("b");
            then(queue.markedPosition()).isEqualTo(1);
            then(queue.dequeueHead()).isEqualTo("c");
            then(queue.markedPosition()).isEqualTo(0);

            then(queue.size()).isEqualTo(0);
        }

        then(queue.markedPosition()).isEqualTo(0);  // a* b c      |abc
    }
}