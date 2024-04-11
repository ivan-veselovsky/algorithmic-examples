package edu.populating_next_pointers;

import edu.common.Node;
import edu.common.TreeNodeUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulateIITest {

    @Test
    void connect() {
        Node root = TreeNodeUtils.buildTreeFromBFS_Node(new Integer[] { 1,2,3,4,5,null,7 });

        new PopulateII().connect(root);

        System.out.println(root);
        //then()
    }

    // [0,2,4,1,null,3,-1,5,1,null,6,null,8]
    //private void checkLinkedCorrectly
}