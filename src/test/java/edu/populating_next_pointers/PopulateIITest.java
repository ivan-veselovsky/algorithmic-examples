package edu.populating_next_pointers;

import edu.common.Node;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

import static edu.common.NodeUtils.asHashSeparatedNodeList;
import static edu.common.NodeUtils.buildTreeFromBFS_Node;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class PopulateIITest {

    @Test
    void connect() {
        Node root = buildTreeFromBFS_Node(new Integer[] { 1,2,3,4,5,null,7 });

        new PopulateII().connect(root);

        Integer[] list = asHashSeparatedNodeList(root);
        then(list).containsExactly(1,null,
                2,3, null,
                4,5,7,null);
    }
}