package edu.networkcart;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkCart {

    record Message(Node source, @NonNull Node destination) {
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

        void send(@Nullable Node source, @NonNull Node destination) {
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
        @Getter
        private final Set<Node> neighbours = new LinkedHashSet<>();

        /** Holds list of nodes we sent the Cart to.
         * Initially this set is empty.
         * In the end of node processing it will contain all neighbours. */
        private final Set<Node> sentNodes = new HashSet<>();
        /** The Node we received the Cart from 1st time.
         * This is also the node the Cart will be sent in the very end, last time we send it.
         * For "start" node this is always null. */
        private @Nullable Node parent;

        private int receiveCount;

        static Node of(Driver driver, String name) {
            return new Node(driver, name);
        }

        @Override
        public String toString() {
            return name;
        }

        /** Source is null when the start node receives the Cart */
        void onReceive(final @Nullable Node source, Cart cart) {
            receiveCount++;
            if (receiveCount == 1) {
                parent = source; // start node does not have a parent.
            }

            Node target = parent;
            if (!sentNodes.contains(source) && source != parent) {
                target = source; // Send it right back.
            } else {
                // Sent it to the 1st node that was not yet processed:
                for (Node neighbour: neighbours()) {
                    if (neighbour != parent && !sentNodes.contains(neighbour)) {
                        target = neighbour;
                        break; // there is only one cart, so we cannot send to multiple neighbours!
                    }
                }
            }

            final boolean isNodeProcessingFinished = (target == parent);
            if (isNodeProcessingFinished) {
                onDone(cart);
            }

            if (target != null) {
                doSend(target);
            }

            if (isNodeProcessingFinished) {
                assert (parent == null) || (target == parent);
                assert neighbours().size() == sentNodes.size() : " Node " + this
                        + ": " + neighbours().size() + " == " + sentNodes.size();
            } else {
                assert (parent == null) || (sentNodes.size() < neighbours().size());
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
