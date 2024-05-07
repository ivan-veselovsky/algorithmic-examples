package edu.test2;

import java.util.Objects;

public class LongestSublist {
     static class SinglyLinkedListNode {
         int data;
         SinglyLinkedListNode next;
     }

     static class Pair {
         final SinglyLinkedListNode ptr;
         final int count;
         Pair(SinglyLinkedListNode ptr, int count) {
             this.ptr = Objects.requireNonNull(ptr);
             this.count = count;
         }
     }

     public static SinglyLinkedListNode locateLongestList(final SinglyLinkedListNode head) {
         SinglyLinkedListNode slow = head;

         // 0. check if there are loops
         final int maxCount = computeMaxLengthToGo(head);

         // 1. go along the entire list and find the longest segment's length
         int maxLength = 1;
         int went = 0;
         while (true) {
             Pair p = runToFirstIncreasingValue(slow, maxCount - went);
             if (p.count > maxLength) {
                 maxLength = p.count;
             }

             went += p.count;
             if (went > maxCount) {
                 break;
             }

             slow = p.ptr.next;
             if (slow == null) {
                 break;
             }
         }

         // 2. go again and find the pointer to the 1st segment of such length:
         slow = head;
         went = 0;
         while (true) {
             Pair p = runToFirstIncreasingValue(slow, maxCount - went);
             if (p.count == maxLength) {
                 p.ptr.next = null; // null the ref to terminate the list
                 return slow;
             }

             went += p.count;
             if (went > maxCount) {
                 break;
             }

             slow = p.ptr.next;
             if (slow == null) {
                 break;
             }
         }

         return head;
    }

    static int computeMaxLengthToGo(final SinglyLinkedListNode head) {
         int count = 0;
         SinglyLinkedListNode slow = head;
         SinglyLinkedListNode fast = head;
         while (true) {
             slow = slow.next;
             fast = fast.next;
             if (fast == null) {
                 break;
             }
             count++;
             fast = fast.next;
             if (fast == null) {
                 break;
             }
             count++;
             if (fast == slow) {
                break; // loop detected
             }
         }
         return count + 1;
    }

    static Pair runToFirstIncreasingValue(SinglyLinkedListNode x, int maxToGo) {
        int count = 1;
        SinglyLinkedListNode successor;
        while (true) {
            successor = x.next;
            if (successor == null || successor.data > x.data) {
                break;
            }
            count++;
            x = successor;
            if (count >= maxToGo) {
                break;
            }
        }
        return new Pair(x, count);
    }

}
