package edu.isbalanced;

import edu.common.NodeP;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class IsBinaryTreeBalancedTest {

    @Test
    void isBalanced() {
        NodeP<Void> node2 = NodeP.of( "2",
                NodeP.of( "4"),
                NodeP.of( "5",
                    NodeP.of("10", null, null),
                    NodeP.of("11")));
        NodeP<Void> node3 = NodeP.of("3",
                NodeP.of("6"), null);

        NodeP<Void> root = NodeP.of("1", node2, node3);

        then(IsBinaryTreeBalanced.isBalanced(root)).isTrue();
    }

    @Test
    void isBalanced2() {
        NodeP<Void> node2 = NodeP.of("2",
                NodeP.of("4"),
                NodeP.of("5",
                        NodeP.of("10", NodeP.of("20"), null),
                        NodeP.of("11")));
        NodeP<Void> node3 = NodeP.of("3",
                NodeP.of("6"), null);

        NodeP<Void> root = NodeP.of("1", node2, node3);

        then(IsBinaryTreeBalanced.isBalanced(root)).isFalse();
    }
}