package edu.max_non_repeating_substring;

import java.util.BitSet;

public class MaxSubstring {
    public int lengthOfLongestSubstring(final String s) {
        final char[] cc = s.toCharArray();
        final Counter counter = new Counter();
        int slow = 0;
        for (int fast = 0; fast < cc.length; fast++) {
            if (!counter.plus(cc[fast])) {
                //System.out.println("not added dupl: " + cc[fast]);
                while (slow < fast) {
                    boolean removedDuplicate = counter.minus(cc[slow]);
                    //System.out.println("removed : "+ cc[slow] + ": " + removedDuplicate);
                    slow++;
                    if (removedDuplicate) {
                        break;
                    }
                }
            } else {
                //System.out.println("added : "+ cc[fast]);
            }
        }
        return counter.maxBitCount;
    }

    static class Counter {
        final BitSet bitVector = new BitSet(128);
        int bitCount = 0;
        int maxBitCount = 0;
        char duplicatedChar = 0;
        boolean plus(char c) { // true if added, false if already present in the bit set
            if (!bitVector.get(c)) {
                bitVector.set(c);
                bitCount++;
                if (bitCount > maxBitCount) {
                    maxBitCount = bitCount;
                }
                return true;
            } else {
                assert duplicatedChar == 0;
                duplicatedChar = c;
                return false;
            }
        }

        boolean minus(char c) {
            assert bitVector.get(c) : "char = " + c + ", bitVector = " + bitVector;
            if (c == duplicatedChar) {
                duplicatedChar = 0;
                return true;
            } else {
                bitVector.clear(c);
                bitCount--;
                return false;
            }
        }
    }
}
