package edu.populating_next_pointers;

import edu.common.Node;
import org.junit.jupiter.api.Test;

import static edu.common.NodeUtils.asHashSeparatedNodeList;
import static edu.common.NodeUtils.buildTreeFromBFS_Node;
import static org.assertj.core.api.BDDAssertions.then;

class PopulateIIBiQueueTest {

    @Test
    void connect() {
        Node root = buildTreeFromBFS_Node(new Integer[] { 1,2,3,4,5,null,7,
            null, null, 10, null, null, null, 14 });

        new PopulateIIBiQueue().connect(root);

        Integer[] list = asHashSeparatedNodeList(root);
        then(list).containsExactly(1,null,
                2,3, null,
                4,5,7,null,
                10,14, null);

    }
}