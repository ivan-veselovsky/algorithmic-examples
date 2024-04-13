package edu.common;

import java.util.ArrayDeque;
import java.util.Deque;

public class RedBallQueue<T> {
    private final Deque<T> deque;
    private int redBallPosition; // virtual "Red Ball"

    public RedBallQueue(int initialCapacity) {
        deque = new ArrayDeque<>(initialCapacity);
    }
    public RedBallQueue(Deque<T> deque) {
        this.deque = deque;
    }

    public void enqueue(T t) {
        deque.offer(t); // does not change the red ball position
    }

    protected T doDequeue() {
        return deque.poll();
    }

    public final T dequeue() {
        T t = doDequeue();
        if (redBallPosition == 0) { // red ball is in the head, => move it to the queue tail
            redBallPosition = size(); // behind the last, maybe 0 again
        } else {
            redBallPosition--;
        }
        return t;
    }

    public final int redBallPosition() {
        return redBallPosition;
    }

    public T peek() {
        return deque.peek();
    }

    public int size() {
        return deque.size();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public Deque<T> getDeque() {
        return deque;
    }
}
