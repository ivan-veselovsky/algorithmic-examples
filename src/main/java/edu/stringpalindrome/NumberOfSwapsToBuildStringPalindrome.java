package edu.stringpalindrome;

public class NumberOfSwapsToBuildStringPalindrome {

    public static int minSwapsRequired(final String s) {
        final int halfLength = s.length() / 2;

        // nb: middle bit, if any, is ignored:
        int fullDiffCount = 0;
        for (int i=0; i<halfLength; i++) {
            boolean left = (s.charAt(i) == '1'); // left part
            boolean reversedRight = (s.charAt(s.length() - 1 - i) == '1');  // reversed right part
            if (left ^ reversedRight) {
                fullDiffCount++;
            }
        }

        if (fullDiffCount == 0) {
            return 0; // already palindrome, no swap needed.
        }

        if (isEven(fullDiffCount)) {
            return fullDiffCount / 2;
        } else {
            if (isEven(s.length())) {
                return -1; // impossible
            } else {
                return 1 + (fullDiffCount - 1) / 2; // use the middle bit
            }
        }
    }

    static boolean isEven(int x) {
        return (x % 2 == 0);
    }

}
