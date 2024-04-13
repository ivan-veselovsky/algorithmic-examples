package edu.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class RedBallQueueTest {

    @Test
    void szie_of_empty() {
        RedBallQueue<Integer> queue = new RedBallQueue<>(1);
        then(queue.size()).isZero();
    }

    @Test
    void basic_dequeue_when_empty() {
        RedBallQueue<Integer> queue = new RedBallQueue<>(1);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.dequeue()).isNull();
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_one_el() {
        RedBallQueue<Integer> queue = new RedBallQueue<>(1);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_two_el() {
        RedBallQueue<Integer> queue = new RedBallQueue<>(2);
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
        RedBallQueue<Integer> queue = new RedBallQueue<>(1);
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
        RedBallQueue<Integer> queue = new RedBallQueue<>(1);
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
        RedBallQueue<Integer> queue = new RedBallQueue<>(1);
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
        RedBallQueue<String> queue = new RedBallQueue<>(1);
        then(queue.size()).isEqualTo(0);
        then(queue.redBallPosition()).isEqualTo(0);  // *          |

        queue.enqueue("a");
        then(queue.redBallPosition()).isEqualTo(0);  // a* b c      |abc
        queue.enqueue("b");
        then(queue.redBallPosition()).isEqualTo(0);  // a* b c      |abc
        queue.enqueue("c");
        then(queue.size()).isEqualTo(3);
        then(queue.redBallPosition()).isEqualTo(0);  // a* b c      |abc

        then(queue.dequeue()).isEqualTo("a");
        then(queue.size()).isEqualTo(2);
        then(queue.redBallPosition()).isEqualTo(2); // b c *        bc|

        queue.enqueue("d");
        then(queue.size()).isEqualTo(3);
        then(queue.redBallPosition()).isEqualTo(2); // b c d*       bc|d

        queue.enqueue("e");
        then(queue.size()).isEqualTo(4);
        then(queue.redBallPosition()).isEqualTo(2); // b c d* e     bc|de

        then(queue.dequeue()).isEqualTo("b");
        then(queue.redBallPosition()).isEqualTo(1); // c d* e       cd|e

        then(queue.dequeue()).isEqualTo("c");
        then(queue.redBallPosition()).isEqualTo(0); // d* e         d|e

        then(queue.dequeue()).isEqualTo("d");
        then(queue.redBallPosition()).isEqualTo(1); // e *          e|

        then(queue.dequeue()).isEqualTo("e");
        then(queue.redBallPosition()).isEqualTo(0); // *             |

        then(queue.isEmpty()).isTrue();
    }

    @Test
    void caterpillar() {
        RedBallQueue<String> queue = new RedBallQueue<>(7);

        for (int i = 0; i < 35; i++) {
            queue.enqueue("a");
            queue.enqueue("b");
            queue.enqueue("c");
            then(queue.size()).isEqualTo(3);
            then(queue.redBallPosition()).isEqualTo(0);

            then(queue.dequeue()).isEqualTo("a");
            then(queue.redBallPosition()).isEqualTo(2);
            then(queue.dequeue()).isEqualTo("b");
            then(queue.redBallPosition()).isEqualTo(1);
            then(queue.dequeue()).isEqualTo("c");
            then(queue.redBallPosition()).isEqualTo(0);

            then(queue.size()).isEqualTo(0);
        }

        then(queue.redBallPosition()).isEqualTo(0);  // a* b c      |abc
    }
}