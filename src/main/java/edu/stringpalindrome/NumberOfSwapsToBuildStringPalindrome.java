package edu.stringpalindrome;

public class NumberOfSwapsToBuildStringPalindrome {

    public static int minSwapsRequired(final String s) {
        final int halfLength = s.length() / 2;

        // nb: middle bit, if any, is ignored:
        int fullDiffCount = 0;
        for (int i=0; i<halfLength; i++) {
            boolean left = (s.charAt(i) == '1'); // left part
            boolean reversedRight = (s.charAt(s.length() - 1 - i) == '1');  // reversed right part
            if (left != reversedRight) {
                fullDiffCount++;
            }
        }

        if (fullDiffCount == 0) {
            return 0; // palindrome, no swap needed.
        }

        int additionalSwap = 0;
        if (s.length() % 2 == 0) {
            if (fullDiffCount % 2 != 0) {
                return -1; // impossible
            }
        } else { // e.g '100' :
            if (fullDiffCount % 2 == 0) {
                // middle bit is not used.
            } else {
                // we have to use the middle bit:
                fullDiffCount--;
                additionalSwap++;
            }
        }

        return additionalSwap + fullDiffCount / 2;
    }

}
