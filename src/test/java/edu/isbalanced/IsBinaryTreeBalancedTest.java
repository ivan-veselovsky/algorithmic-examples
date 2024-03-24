package edu.isbalanced;

import edu.common.Node;
import edu.isbalanced.IsBinaryTreeBalanced.LeftRight;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.assertj.core.api.BDDAssertions.then;

class IsBinaryTreeBalancedTest {

    @Test
    void isBalanced() {
        final Supplier<AtomicReference<LeftRight>> sup = AtomicReference::new;
        Node<AtomicReference<LeftRight>> node2 = Node.of(sup, "2",
                Node.of(sup, "4"),
                Node.of(sup, "5",
                    Node.of(sup, "10", null, null),
                    Node.of(sup, "11")));
        Node<AtomicReference<LeftRight>> node3 = Node.of(sup, "3",
                Node.of(sup, "6"), null);

        Node<AtomicReference<LeftRight>> root = Node.of(sup, "1", node2, node3);

        then(IsBinaryTreeBalanced.isBalanced(root)).isTrue();
    }

    @Test
    void isBalanced2() {
        final Supplier<AtomicReference<LeftRight>> sup = AtomicReference::new;
        Node<AtomicReference<LeftRight>> node2 = Node.of(sup, "2",
                Node.of(sup, "4"),
                Node.of(sup, "5",
                        Node.of(sup, "10", Node.of(sup, "20"), null),
                        Node.of(sup, "11")));
        Node<AtomicReference<LeftRight>> node3 = Node.of(sup, "3",
                Node.of(sup, "6"), null);

        Node<AtomicReference<LeftRight>> root = Node.of(sup, "1", node2, node3);

        then(IsBinaryTreeBalanced.isBalanced(root)).isFalse();
    }
}