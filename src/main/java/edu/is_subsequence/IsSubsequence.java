package edu.is_subsequence;

public class IsSubsequence {
    public boolean isSubsequence(String s, String t) {
        if (s.length() > t.length()) {
            return false;
        }
        if (s.isEmpty()) {
            return true;
        }
        if (t.length() == s.length()) {
            return s.equals(t);
        }

        char[] sub = s.toCharArray();
        char[] all = t.toCharArray();

        int subIndex = 0;
        int allIndex = 0;
        while (subIndex < sub.length) {
            allIndex = findChar(sub[subIndex], allIndex, all);
            if (allIndex < 0) {
                return false;
            }
            subIndex++;
            allIndex++;
        }
        return true;
    }

    int findChar(char c, int from, char[] cc) {
        int i = from;
        while (i < cc.length) {
            if (cc[i] == c) {
                return i;
            }
            i++;
        }
        return -1;
    }

}
