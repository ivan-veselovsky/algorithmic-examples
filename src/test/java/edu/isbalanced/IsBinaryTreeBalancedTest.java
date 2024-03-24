package edu.isbalanced;

import edu.common.Node;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class IsBinaryTreeBalancedTest {

    @Test
    void isBalanced() {
        Node<Void> node2 = Node.of( "2",
                Node.of( "4"),
                Node.of( "5",
                    Node.of("10", null, null),
                    Node.of("11")));
        Node<Void> node3 = Node.of("3",
                Node.of("6"), null);

        Node<Void> root = Node.of("1", node2, node3);

        then(IsBinaryTreeBalanced.isBalanced(root)).isTrue();
    }

    @Test
    void isBalanced2() {
        Node<Void> node2 = Node.of("2",
                Node.of("4"),
                Node.of("5",
                        Node.of("10", Node.of("20"), null),
                        Node.of("11")));
        Node<Void> node3 = Node.of("3",
                Node.of("6"), null);

        Node<Void> root = Node.of("1", node2, node3);

        then(IsBinaryTreeBalanced.isBalanced(root)).isFalse();
    }
}