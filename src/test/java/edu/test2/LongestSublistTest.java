package edu.test2;

import org.junit.jupiter.api.Test;
import edu.test2.LongestSublist.SinglyLinkedListNode;

import static org.assertj.core.api.BDDAssertions.then;

class LongestSublistTest {

    @Test
    void locateLongestList() {
        LongestSublist.SinglyLinkedListNode n7 = chain(3, null);
        LongestSublist.SinglyLinkedListNode n6 = chain(1, n7);
        LongestSublist.SinglyLinkedListNode n5 = chain(2, n6);
        LongestSublist.SinglyLinkedListNode n4 = chain(2, n5);
        LongestSublist.SinglyLinkedListNode n3 = chain(5, n4);
        LongestSublist.SinglyLinkedListNode n2 = chain(4, n3);
        LongestSublist.SinglyLinkedListNode n1 = chain(3, n2);

        SinglyLinkedListNode actual = LongestSublist.locateLongestList(n1);
        then(actual).isSameAs(n3);
    }

    private LongestSublist.SinglyLinkedListNode chain(int v, LongestSublist.SinglyLinkedListNode x) {
        LongestSublist.SinglyLinkedListNode n = new LongestSublist.SinglyLinkedListNode();
        n.data = v;
        n.next = x;
        return n;
    }
}