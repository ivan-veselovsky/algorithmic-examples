package edu.networkcart;

import lombok.val;
import org.junit.jupiter.api.Test;

import edu.networkcart.NetworkCart.*;

import static org.assertj.core.api.BDDAssertions.then;

class NetworkCartTest {

    private static void linkNodes(Node x, Node y) {
        x.getNeighbours().add(y);
        y.getNeighbours().add(x);
    }

    @Test
    void should_correctly_process_basic_loop() {
        final Cart cart = new Cart();
        final Driver driver = new Driver(cart);

        Node a = Node.of(driver,"a");
        Node b = Node.of(driver, "b");
        Node c = Node.of(driver, "c");
        linkNodes(a, b);
        linkNodes(b, c);
        linkNodes(c, a);

        Node d = Node.of(driver, "d");
        linkNodes(a, d);

        Node start = Node.startOf(driver, "start");
        linkNodes(a, start);

        start.doSend(a);
        driver.processMessagesInALoop();

        then(cart.getNames()).containsExactly("c", "b", "d", "a", "start");
    }

    @Test
    void more_complex_loop() {
        val cart = new Cart();
        val driver = new Driver(cart);

        val a = Node.of(driver,"a");
        val b = Node.of(driver, "b");
        val c = Node.of(driver, "c");
        val e = Node.of(driver, "e");
        val f = Node.of(driver, "f");
        linkNodes(a, b);
        linkNodes(b, c);
        linkNodes(c, a);

        linkNodes(b, e);
        linkNodes(c, e);
        linkNodes(c, f);
        linkNodes(e, f);

        val d = new Node(driver, "d", false);
        linkNodes(a, d);

        val start = new Node(driver, "start", true);
        linkNodes(a, start);

        start.doSend(a);
        driver.processMessagesInALoop();

        then(cart.getNames()).containsExactly("f", "e", "c", "b", "d", "a", "start");
    }

}