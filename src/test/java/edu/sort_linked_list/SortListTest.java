package edu.sort_linked_list;

import edu.common.ListNode;
import org.junit.jupiter.api.Test;

import static edu.sort_linked_list.SortList.*;
import static org.assertj.core.api.BDDAssertions.then;

class SortListTest {

    @Test
    void test0() {
        ListNode n3 = new ListNode(3);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);

        then(size(n1)).isEqualTo(3);
        then(toStringList(n1)).isEqualTo("1 -> 2 -> 3 -> null");
    }

    @Test
    void sortList() {
        ListNode n4 = new ListNode(0);
        ListNode n3 = new ListNode(1, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(3, n2);

        ListNode sorted = new SortList().sortList(n1);
        then(toStringList(sorted)).isEqualTo("0 -> 1 -> 2 -> 3 -> null");
    }

    @Test
    void sortList8() {
        ListNode n7 = new ListNode(5);
        ListNode n6 = new ListNode(0, n7);
        ListNode n5 = new ListNode(2, n6);
        ListNode n4 = new ListNode(7, n5);
        ListNode n3 = new ListNode(4, n4);
        ListNode n2 = new ListNode(6, n3);
        ListNode n1 = new ListNode(1, n2);
        ListNode n0 = new ListNode(3, n1);

        ListNode sorted = new SortList().sortList(n0);
        then(toStringList(sorted)).isEqualTo("0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null");
    }

    @Test
    void should_cut_properly() {
        ListNode n7 = new ListNode(7);
        ListNode n6 = new ListNode(6, n7);
        ListNode n5 = new ListNode(5, n6);
        ListNode n4 = new ListNode(4, n5);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);
        ListNode n0 = new ListNode(0, n1);

        then(cut(n0, 0)).isSameAs(n0);

        then(cut(n0, 1)).isSameAs(n1);
        then(size(n0)).isEqualTo(1);
        then(n0.next()).isNull();

        then(cut(n0, 0)).isSameAs(n0);
        then(cut(n0, 1)).isNull();

        then(size(n1)).isEqualTo(7);
        then(cut(n1, 7)).isNull();

        then(cut(n1, 25)).isNull();
        then(size(n1)).isEqualTo(7);
    }

    @Test
    void should_sort_arbitrary_length3() {
        ListNode n2 = new ListNode(6, null);
        ListNode n1 = new ListNode(1, n2);
        ListNode n0 = new ListNode(3, n1);

        ListNode sorted = new SortList().sortList(n0);
        then(toStringList(sorted)).isEqualTo("1 -> 3 -> 6 -> null");
    }

    @Test
    void should_sort_arbitrary_length7() {
        //ListNode n7 = new ListNode(5);
        ListNode n6 = new ListNode(0, null);
        ListNode n5 = new ListNode(2, n6);
        ListNode n4 = new ListNode(7, n5);
        ListNode n3 = new ListNode(4, n4);
        ListNode n2 = new ListNode(6, n3);
        ListNode n1 = new ListNode(1, n2);
        ListNode n0 = new ListNode(3, n1);

        ListNode sorted = new SortList().sortList(n0);
        then(toStringList(sorted)).isEqualTo("0 -> 1 -> 2 -> 3 -> 4 -> 6 -> 7 -> null");
    }

    @Test
    void should_sort_single_node_list() {
        ListNode n0 = new ListNode(3);

        ListNode sorted = new SortList().sortList(n0);
        then(toStringList(sorted)).isEqualTo("3 -> null");
    }

    @Test
    void should_sort_null() {
        ListNode sorted = new SortList().sortList(null);
        then(toStringList(sorted)).isEqualTo("null");
    }

}