package edu.longest_prefix;

public class LongestPrefix {
    public String longestCommonPrefix(String[] strs) {
        int minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            if (str.length() < minLength) {
                minLength = str.length();
            }
        }

        int index = 0;
        Character c;
        outer: for (int j=0; j<minLength; j++) {
            c = null;
            for (String wordStr : strs) {
                if (index >= wordStr.length()) {
                    break outer;
                }
                if (c == null) {
                    c = wordStr.charAt(index);
                } else {
                    if (wordStr.charAt(index) != c) {
                        break outer;
                    }
                }
            }
            index++;
        }

        return strs[0].substring(0, index);
    }
}
