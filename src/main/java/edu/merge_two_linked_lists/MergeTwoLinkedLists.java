package edu.merge_two_linked_lists;

import edu.common.ListNode;

public class MergeTwoLinkedLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) {
            return (list1 == null) ? list2 : list1;
        }
        final ListNode result;
        ListNode x = list1;
        ListNode y = list2;
        if (list1.val() < list2.val()) {
            result = list1;
            x = x.next();
        } else {
            result = list2;
            y = y.next();
        }
        ListNode accum = result;
        while (true) {
            if (x != null && y != null) {
                if (x.val() < y.val()) {
                    accum.next(x);
                    x = x.next();
                } else {
                    accum.next(y);
                    y = y.next();
                }
                accum = accum.next();
            } else {
                ListNode only = (x == null) ? y : x;
                accum.next(only);
                break;
            }
        }
        return result;
    }
}
