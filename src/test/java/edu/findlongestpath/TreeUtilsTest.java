package edu.findlongestpath;

import org.junit.jupiter.api.Test;

import static edu.findlongestpath.TreeUtils.*;
import static org.assertj.core.api.BDDAssertions.*;

class TreeUtilsTest {

    @Test
    void testMajoratingPowerOf2() {
        then(majoratingPowerOf2(0)).isEqualTo(0);
        then(majoratingPowerOf2(1)).isEqualTo(0);

        then(majoratingPowerOf2(2)).isEqualTo(1);

        then(majoratingPowerOf2(3)).isEqualTo(2);
        then(majoratingPowerOf2(4)).isEqualTo(2);

        then(majoratingPowerOf2(5)).isEqualTo(3);
        then(majoratingPowerOf2(8)).isEqualTo(3);

        then(majoratingPowerOf2(15)).isEqualTo(4);
        then(majoratingPowerOf2(16)).isEqualTo(4);

        then(majoratingPowerOf2(17)).isEqualTo(5);
    }

    @Test
    void testContainedPowerOf2() {
        then(containedPowerOf2(0)).isEqualTo(0);
        then(containedPowerOf2(1)).isEqualTo(0);

        then(containedPowerOf2(2)).isEqualTo(1);

        then(containedPowerOf2(3)).isEqualTo(1);
        then(containedPowerOf2(4)).isEqualTo(2);

        then(containedPowerOf2(5)).isEqualTo(2);
        then(containedPowerOf2(6)).isEqualTo(2);
        then(containedPowerOf2(8)).isEqualTo(3);

        then(containedPowerOf2(15)).isEqualTo(3);
        then(containedPowerOf2(16)).isEqualTo(4);

        then(containedPowerOf2(17)).isEqualTo(4);
    }


    @Test
    void testcountNodes() {
         Node node = parseTree("o" +
                "o  o" +
                "_ o _ o" +
                "__ o_ __ o_");
        int count = countNodes(node);
        then(count).isEqualTo(7);
        int depth = depth(node);
        then(depth).isEqualTo(3);

        node = parseTree("o");
        count = countNodes(node);
        then(count).isEqualTo(1);
        depth = depth(node);
        then(depth).isEqualTo(0);

        node = parseTree("o" +
                "- o" +
                "-- o-" +
                "---- -o--" +
                "---- ---- ---o ----");
        count = countNodes(node);
        then(count).isEqualTo(5);
        depth = depth(node);
        then(depth).isEqualTo(4);
    }

    @Test
    void testPrintTree() {
        Node node = parseTree("o" +
                                       "o  o" +
                                     "o _ _ o" +
                                   "__ __ __ o_" +
                                 "____ ____ ____ oo__"
        );
        then(depth(node)).isEqualTo(4);
        printTree(node);
    }

    @Test
    void orphanSubTree() {
        Node node = parseTree(
                "o" +
                        "o  _" +
                        "o o _ _" +
                        "oo oo __ __"+
                        "oooo oooo");
        printTree(node);
        then(depth(node)).isEqualTo(4);
        then(countNodes(node)).isEqualTo(16);

        thenExceptionOfType(IllegalStateException.class).isThrownBy(() -> parseTree(
                "o" +
                        "o  _" +
                        "o o o o" +
                        "oo oo oo oo"+
                        "oooo oooo oooo oooo")).withMessageContaining("expected 30 != actual 16");
    }
}