package edu.networkcart;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkCart {

    record Message(Node source, Node destination) {
        @Override
        public String toString() {
            return source  + " -> " + destination;
        }
    }

    @RequiredArgsConstructor
    static class Driver {
        @NonNull
        private final Cart cart;
        private final AtomicReference<Message> messageRef = new AtomicReference<>();

        void send(@NonNull Node source, @NonNull Node destination) {
            Message message = new Message(source, destination);
            Preconditions.checkState(messageRef.compareAndSet(null, message),
                        "failed to send: " + message);
        }

        void processMessagesInALoop() {
            int counter = 0;
            Message message;
            while ((message = messageRef.getAndSet(null)) != null) {
                counter++;
                System.out.println("Message #" + counter + ": " + message);
                message.destination().onReceive(message.source(), cart);
            }
        }
    }

    @RequiredArgsConstructor
    static class Node {
        private final Driver driver;
        @Getter
        private final String name;
        @Getter @NonNull
        private final Set<Node> neighbours = new LinkedHashSet<>();

        private final Set<Node> sentNodes = new HashSet<>();
        private final boolean isStart;
        private Node parent;

        @Override
        public String toString() {
            return name;
        }

        void onReceive(final @NonNull Node source, Cart cart) {
            if (parent == null && !isStart) {
                parent = source;
            }

            Node target = parent;
            if (!sentNodes.contains(source) && source != parent) {
                target = source; // sent it right back
            } else {
                for (Node neighbour: getNeighbours()) {
                    if (neighbour != parent && !sentNodes.contains(neighbour)) {
                        target = neighbour;
                        break; // we cannot send to multiple neighbours
                    }
                }
            }

            final boolean isDone = (target == parent);
            if (isDone) {
                if (!isStart) { // when start node receives the message back,
                    // we need to exist.
                    doSend(parent);
                }
                onDone(cart);
            } else {
                doSend(target);
            }

            if (!isStart) {
                if (isDone) {
                    assert getNeighbours().size() == sentNodes.size() : " Node " + this
                            + ": " + getNeighbours().size() + " == " + sentNodes.size();
                } else {
                    assert sentNodes.size() < getNeighbours().size();
                }
            }
        }

        void doSend(@NonNull Node target) {
            Preconditions.checkState(target != this);
            driver.send(this, target);

            boolean added = sentNodes.add(target);
            assert added : "Node: " + this + ", target = " + target;
        }

        void onDone(Cart cart) {
            cart.add(name);
            System.out.println(name + " done!");
        }
    }

    static class Cart {
        private final List<String> collection = new LinkedList<>();
        void add(String name) {
            collection.add(name);
        }
        public List<String> getNames() {
            return List.copyOf(collection);
        }
    }
}
