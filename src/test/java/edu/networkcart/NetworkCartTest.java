package edu.networkcart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class NetworkCartTest {

    private static void linkNodes(NetworkCart.Node x, NetworkCart.Node y) {
        x.getNeighbours().add(y);
        y.getNeighbours().add(x);
    }

    @Test
    void should_correctly_process_basic_loop() {
        final NetworkCart.Cart cart = new NetworkCart.Cart();
        final NetworkCart.Driver driver = new NetworkCart.Driver(cart);

        NetworkCart.Node a = new NetworkCart.Node(driver,"a", false);
        NetworkCart.Node b = new NetworkCart.Node(driver, "b", false);
        NetworkCart.Node c = new NetworkCart.Node(driver, "c", false);
        linkNodes(a, b);
        linkNodes(b, c);
        linkNodes(c, a);

        NetworkCart.Node d = new NetworkCart.Node(driver, "d", false);
        linkNodes(a, d);

        NetworkCart.Node start = new NetworkCart.Node(driver, "start", true);
        linkNodes(a, start);

        start.doSend(a);
        driver.processMessagesInALoop();

        then(cart.getNames()).containsExactly("c", "b", "d", "a", "start");
    }

}