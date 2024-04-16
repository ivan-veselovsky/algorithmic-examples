package edu.insertion_sort_linked_list;

import edu.common.ListNode;

public class InsertionSortOnLinkedList {

    static class Solution {
        private ListNode head;

        public ListNode insertionSortList(final ListNode head0) {
            head = head0;

            ListNode lastSorted = head; // and of the sorted part, it has the maximum value among all processed.

            while (true) {
                if (lastSorted.next() == null) {
                    return head; // we're done
                } else if (lastSorted.next().val() >= lastSorted.val()) {
                    lastSorted = lastSorted.next(); // shortcut
                } else {
                    // cut and insert somewhere inside the sorted part:
                    ListNode victim = cutNodeAfter(lastSorted);
                    findPositionAndInsert(victim);
                }
            }
        }

        ListNode cutNodeAfter(ListNode x) {
            ListNode victim = x.next();
            x.next(victim.next());
            victim.next(null);
            return victim;
        }

        void findPositionAndInsert(final ListNode candidate) {
            ListNode predecessor = null;
            ListNode x = head;
            while (true) {
                if (candidate.val() <= x.val()) {
                    // insert candidate after predecessor and before x:
                    if (predecessor == null) {
                        head = candidate;
                    } else {
                        assert predecessor.next() == x;
                        predecessor.next(candidate);
                    }
                    candidate.next(x);
                    return;
                }
                predecessor = x;
                x = x.next();
                assert x != null; // this case should be considered separately.
            }
        }
    }
}
