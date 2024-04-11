package edu.populating_next_pointers;

import edu.common.Node;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

class PopulateIIBiQueueTest {

    @Test
    void connect() {
        Node root = TreeNodeUtils.buildTreeFromBFS_Node(new Integer[] { 1,2,3,4,5,null,7,
            null, null, 10, null, null, null, 14 });

        new PopulateIIBiQueue().connect(root);

        System.out.println(root);
    }
}