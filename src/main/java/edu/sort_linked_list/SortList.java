package edu.sort_linked_list;

import edu.common.ListNode;
import lombok.Getter;

import java.util.Objects;

public class SortList {

    public ListNode sortList(final ListNode head) {
        ListNode list = head;
        final int totalLength = size(list);
        //System.out.println(" before merging groups : list1 = " + toStringList(list));
        int groupSize = 1;
        do {
            list = mergeAllGroupPairs(groupSize, list, totalLength);
            //System.out.println(" after  merging groups " + groupSize + ": " + toStringList(list));
            assert size(list) == totalLength : size(list);
            groupSize <<= 1;
        } while (groupSize < totalLength);
        return list;
    }

    ListNode mergeAllGroupPairs(final int groupSize, final ListNode start, int totalLength) {
        ListNode x = start;
        final AppendableList accumulator = new AppendableList(null);
        while (true) {
            ListNode y = cut(x, groupSize);
            ListNode z = cut(y, groupSize);
            mergeTwoLists(accumulator, x, y);

            if (z == null) {
                break;
            }
            x = z;
        }
        return accumulator.beginning();
    }

    void mergeTwoLists(final AppendableList accumulator, ListNode x, ListNode y) {
        final int initialSize = accumulator.size();
        int xCount = 0;
        int yCount = 0;
        while (true) {
            boolean advanceX = (x != null);
            boolean advanceY = (y != null);
            if (!advanceX && !advanceY) {
                break;
            }
            if (advanceX && advanceY) {
                advanceX = (x.val() < y.val());
            }
            if (advanceX) {
                accumulator.append(x);
                x = x.next();
                xCount++;
            } else {
                accumulator.append(y);
                y = y.next();
                yCount++;
            }
        }
        assert accumulator.size() - initialSize == xCount + yCount;
    }

    @Getter
    static class AppendableList {
        ListNode beginning;
        ListNode last;
        int size;
        AppendableList(ListNode beginning) {
            this.beginning = beginning;
            this.last = beginning;
            size = (beginning == null) ? 0 : 1;
            checkInvariants();
        }
        void append(ListNode n) {
            n = Objects.requireNonNull(n);
            if (beginning == null) {
                beginning = n;
            } else {
                last.next(n);
            }
            last = n;
            size++;
            checkInvariants();
        }
        private void checkInvariants() {
            if (size == 0) {
                assert beginning == null && last == null;
            } else {
                assert beginning != null && last != null;
            }
        }
    }

    /** Cuts a starting part of the list.
     * @return pointer to the remainder after cut. */
    static ListNode cut(ListNode x, int countToSkip) {
        if (countToSkip == 0) {
            return x;
        }
        int count = 0;
        ListNode tail = x;
        while ((x != null) && (count < countToSkip)) {
            tail = x;
            x = x.next();
            count++;
        }
        if (tail != null) {
            tail.next(null); // cut, not just skip.
        }
        return x;
    }

    static int size(ListNode x) {
        int count = 0;
        while (x != null) {
            x = x.next();
            count++;
        }
        return count;
    }

    // ----------------------------

    static String toStringList(ListNode n) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append( toString(n) );
            if (n == null) {
                break;
            }
            n = n.next();
            sb.append(" -> ");
        }
        return sb.toString();
    }

    static String toString(ListNode n) {
        if (n == null) {
            return "null";
        } else {
            return Integer.toString(n.val());
        }
    }
}
