package edu.listofdepths;

import edu.common.NodeP;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class SortedArrayToBinaryTreeTest {

    @Test
    void test1() {
        NodeP<Void> node2 = NodeP.of("2", NodeP.of("4"), NodeP.of("5"));
        NodeP<Void> node3 = NodeP.of("3", NodeP.of("6"), NodeP.of("7"));

        NodeP<Void> root = NodeP.of("1", node2, node3);

        List<List<NodeP<Void>>> list = ListOfDepths.makeListOfDepths(root);
        list.forEach(level ->
            {
                System.out.print("level: ");
                level.forEach(n -> { System.out.print(n + " "); });
                System.out.println();
            });

        then(list).hasSize(3);
        then(list.get(0).stream().map(NodeP::toString)).containsExactly("1");
        then(list.get(1).stream().map(NodeP::toString)).containsExactly("2", "3");
        then(list.get(2).stream().map(NodeP::toString)).containsExactly("4", "5", "6", "7");
    }

}