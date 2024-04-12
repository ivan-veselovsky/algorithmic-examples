package edu.common;

import java.util.ArrayDeque;
import java.util.Deque;

public class RedBallArrayDeque<T> {
    private final Deque<T> deque;
    private int redBallPosition; // virtual "Red Ball"

    public RedBallArrayDeque(int initialCapacity) {
        deque = new ArrayDeque<>(initialCapacity);
    }

    public void enqueue(T t) {
        deque.offer(t); // does not change the red ball position
    }

    public T dequeue() {
        T t = deque.poll();
        redBallPosition--;
        if (redBallPosition < 0) { // red ball is in the head, => move it to the queue tail
            redBallPosition = deque.size(); // behind the last
        }
        return t;
    }

    public int getRedBallPosition() {
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
}
