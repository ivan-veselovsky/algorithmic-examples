package edu.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ListNode {
    final int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "[" + val + "]";
    }
}
