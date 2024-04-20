package edu.zigzag_conversion;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 *
 * Write the code that will take a string and make this conversion given a number of rows:
 *
 * string convert(string s, int numRows);
 *
 *
 * Example 1:
 *
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 * Example 2:
 *
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * Example 3:
 *
 * Input: s = "A", numRows = 1
 * Output: "A"
 */
public class ZigzagConversion {
    int n; // half-period
    int period; // period
    int zigZags;
    char[] cc;

    public String convert(String s, int numRows) {
        if (numRows <= 1 || s.length() <= 2) {
            return s;
        }

        this.cc = s.toCharArray();
        this.n = numRows - 1; // half-period
        this.period = 2 * n; // period
        this.zigZags = getNumberOfZigZags();

        StringBuilder sb = new StringBuilder(s.length());
        for (int k=0; k<numRows; k++) { // k -- linde of line
            getLine(k, sb);
        }

        return sb.toString();
    }

    private void getLine(int k, StringBuilder sb) {
        for (int i=0; i<=zigZags; i++) {
            if (k > 0 && k < n) {
                if (i > 0) {
                    int diag = period * i - k;
                    if (diag >= cc.length) {
                        return;
                    }
                    sb.append(cc[diag]);
                }
            }

            int straight = period * i + k;
            if (straight >= cc.length) {
                return;
            }
            sb.append(cc[straight]);
        }
    }

    private int getNumberOfZigZags() {
        int zz = cc.length / period;
        int reminder = cc.length % period;
        if (reminder > 0) {
            zz++;
        }
        return zz;
    }

}
