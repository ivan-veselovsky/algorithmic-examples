package edu.common;

import org.junit.jupiter.api.Test;

import java.util.function.IntFunction;

import static org.assertj.core.api.BDDAssertions.then;

class MyArrayQueueTest {

    static class RedBallQueue2<T> extends RedBallQueue<T> {
        private final MyArrayQueue<T> myArrayQueue;
        
        RedBallQueue2(int initialArrayLength, IntFunction<T[]> arrayCreator) {
            super(initialArrayLength);
            myArrayQueue = new MyArrayQueue<>(initialArrayLength, arrayCreator);
        }

        @Override
        public void enqueue(T t) {
            myArrayQueue.enqueueTail(t);
        }

        @Override
        protected T doDequeue() {
            return myArrayQueue.dequeueHead();
        }

        @Override
        public int size() {
            return myArrayQueue.size();
        }

        @Override
        public boolean isEmpty() {
            return myArrayQueue.isEmpty();
        }

        @Override
        public T peek() {
            return myArrayQueue.peek();
        }
    }
    
    @Test
    void szie_of_empty() {
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(1, Integer[]::new);
        then(queue.size()).isZero();
    }

    @Test
    void basic_dequeue_when_empty() {
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(1, Integer[]::new);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.dequeue()).isNull();
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_one_el() {
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(1, Integer[]::new);
        queue.enqueue(51);
        then(queue.size()).isEqualTo(1);
        then(queue.dequeue()).isEqualTo(51);
        then(queue.size()).isEqualTo(0);
    }

    @Test
    void basic_storage_two_el() {
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(2, Integer[]::new);
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
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(1, Integer[]::new);
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
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(1, Integer[]::new);
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
        RedBallQueue2<Integer> queue = new RedBallQueue2<>(1, Integer[]::new);
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
        RedBallQueue2<String> queue = new RedBallQueue2<>(1, String[]::new);
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
        RedBallQueue2<String> queue = new RedBallQueue2<>(7, String[]::new);

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