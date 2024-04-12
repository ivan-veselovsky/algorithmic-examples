package edu.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class RedBallDequeTest {

    @Test
    void szie_of_empty() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(1);
        then(queue.size()).isZero();
    }

    @Test
    void basic_dequeue_when_empty() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(1);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.dequeue()).isNull();
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_one_el() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(1);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_two_el() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(2);
        queue.enqueue(51);
        queue.enqueue(52);
        then(queue.size()).isEqualTo(2);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.dequeue()).isEqualTo(52);
        then(queue.size()).isEqualTo(0);
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void basic_storage_resize() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(1);
        queue.enqueue(51);
        queue.enqueue(52);
        then(queue.size()).isEqualTo(2);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.dequeue()).isEqualTo(52);
        then(queue.size()).isEqualTo(0);
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void basic_storage_2_resizes() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(1);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        queue.enqueue(52);
        then(queue.size()).isEqualTo(2);
        queue.enqueue(53);
        then(queue.size()).isEqualTo(3);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.size()).isEqualTo(2);
        then(queue.dequeue()).isEqualTo(52);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(53);
        then(queue.size()).isEqualTo(0);
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void basic_storage_2_resizes_peek() {
        RedBallArrayDeque<Integer> queue = new RedBallArrayDeque<>(1);
        queue.enqueue(51);
        then(queue.peek()).isEqualTo(51);
        queue.enqueue(52);
        then(queue.peek()).isEqualTo(51);
        queue.enqueue(53);
        then(queue.peek()).isEqualTo(51);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.peek()).isEqualTo(52);
        then(queue.dequeue()).isEqualTo(52);
        then(queue.peek()).isEqualTo(53);
        then(queue.dequeue()).isEqualTo(53);
        then(queue.peek()).isNull();
        then(queue.isEmpty()).isTrue();
    }

    @Test
    void separator_1() {
        RedBallArrayDeque<String> queue = new RedBallArrayDeque<>(1);
        then(queue.size()).isEqualTo(0);
        then(queue.getRedBallPosition()).isEqualTo(0);  // *          |

        queue.enqueue("a");
        then(queue.getRedBallPosition()).isEqualTo(0);  // a* b c      |abc
        queue.enqueue("b");
        then(queue.getRedBallPosition()).isEqualTo(0);  // a* b c      |abc
        queue.enqueue("c");
        then(queue.size()).isEqualTo(3);
        then(queue.getRedBallPosition()).isEqualTo(0);  // a* b c      |abc

        then(queue.dequeue()).isEqualTo("a");
        then(queue.size()).isEqualTo(2);
        then(queue.getRedBallPosition()).isEqualTo(2); // b c *        bc|

        queue.enqueue("d");
        then(queue.size()).isEqualTo(3);
        then(queue.getRedBallPosition()).isEqualTo(2); // b c d*       bc|d

        queue.enqueue("e");
        then(queue.size()).isEqualTo(4);
        then(queue.getRedBallPosition()).isEqualTo(2); // b c d* e     bc|de

        then(queue.dequeue()).isEqualTo("b");
        then(queue.getRedBallPosition()).isEqualTo(1); // c d* e       cd|e

        then(queue.dequeue()).isEqualTo("c");
        then(queue.getRedBallPosition()).isEqualTo(0); // d* e         d|e

        then(queue.dequeue()).isEqualTo("d");
        then(queue.getRedBallPosition()).isEqualTo(1); // e *          e|

        then(queue.dequeue()).isEqualTo("e");
        then(queue.getRedBallPosition()).isEqualTo(0); // *             |

        then(queue.isEmpty()).isTrue();
    }

    @Test
    void caterpillar() {
        RedBallArrayDeque<String> queue = new RedBallArrayDeque<>(7);

        for (int i = 0; i < 35; i++) {
            queue.enqueue("a");
            queue.enqueue("b");
            queue.enqueue("c");
            then(queue.size()).isEqualTo(3);
            then(queue.getRedBallPosition()).isEqualTo(0);

            then(queue.dequeue()).isEqualTo("a");
            then(queue.getRedBallPosition()).isEqualTo(2);
            then(queue.dequeue()).isEqualTo("b");
            then(queue.getRedBallPosition()).isEqualTo(1);
            then(queue.dequeue()).isEqualTo("c");
            then(queue.getRedBallPosition()).isEqualTo(0);

            then(queue.size()).isEqualTo(0);
        }

        then(queue.getRedBallPosition()).isEqualTo(0);  // a* b c      |abc
    }
}