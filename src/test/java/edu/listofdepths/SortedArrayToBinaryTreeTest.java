package edu.listofdepths;

import edu.common.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class SortedArrayToBinaryTreeTest {

    @Test
    void test1() {
        Node<Void> node2 = Node.of("2", Node.of("4"), Node.of("5"));
        Node<Void> node3 = Node.of("3", Node.of("6"), Node.of("7"));

        Node<Void> root = Node.of("1", node2, node3);

        List<List<Node<Void>>> list = ListOfDepths.makeListOfDepths(root);
        list.forEach(level ->
            {
                System.out.print("level: ");
                level.forEach(n -> { System.out.print(n + " "); });
                System.out.println();
            });

        then(list).hasSize(3);
        then(list.get(0).stream().map(Node::toString)).containsExactly("1");
        then(list.get(1).stream().map(Node::toString)).containsExactly("2", "3");
        then(list.get(2).stream().map(Node::toString)).containsExactly("4", "5", "6", "7");
    }

}