package edu.insertion_sort_linked_list;

import edu.common.ListNode;
import org.junit.jupiter.api.Test;

import edu.insertion_sort_linked_list.InsertionSortOnLinkedList.*;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class InsertionSortOnLinkedListTest {

    @Test
    void testSimple() {
        ListNode n3 = new ListNode(3);
        ListNode n1 = new ListNode(1, n3);
        ListNode n2 = new ListNode(2, n1);
        ListNode n4 = new ListNode(4, n2);

        ListNode head = new Solution().insertionSortList(n4);

        then(asList(head)).containsExactly(n1, n2, n3, n4);
    }

    @Test
    void testAlreadySorted() {
        ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);

        ListNode head = new Solution().insertionSortList(n1);

        then(asList(head)).containsExactly(n1, n2, n3, n4);
    }

    @Test
    void testSortedInReverseOrder() {
        ListNode n4 = new ListNode(1);
        ListNode n3 = new ListNode(2, n4);
        ListNode n2 = new ListNode(3, n3);
        ListNode n1 = new ListNode(4, n2);

        ListNode head = new Solution().insertionSortList(n1);

        then(asList(head)).containsExactly(n4, n3, n2, n1);
    }

    List<ListNode> asList(ListNode head) {
        ListNode x = head;
        List<ListNode> list = new LinkedList<>();
        while (x != null) {
            list.add(x);
            x = x.next();
        }
        return List.copyOf(list);
    }
}