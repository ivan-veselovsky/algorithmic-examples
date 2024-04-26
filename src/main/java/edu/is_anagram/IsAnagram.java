package edu.is_anagram;

public class IsAnagram {
    public boolean isAnagram(final String s, final String t) {
        if (s.length() != t.length()) {
            return false;
        }
        return compare(count(s), count(t));
    }

    static int[] count(String x) {
        int[] counts = new int[26];
        for (char c: x.toCharArray()) {
            counts[c - 'a']++;
        }
        return counts;
    }

    static boolean compare(int[] c1, int[] c2) {
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]) {
                return false;
            }
        }
        return true;
    }
}
