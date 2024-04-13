package edu.is_palindrome;

public class IsPalindrome {
    public boolean isPalindrome(String s) {
        String canonical = toCanonialForm(s);

        return isPalindropme(canonical);
    }

    String toCanonialForm(String x) {
        StringBuilder sb = new StringBuilder(x.length());
        for (char c: x.toCharArray()) {
            if (((c >= '0') && (c <= '9'))
                    || ((c >= 'a') && (c <= 'z'))) {
                sb.append(c);
            } else if ((c >= 'A') && (c <= 'Z')) {
                c = Character.toLowerCase(c);
                sb.append(c);
            }
        }
        return sb.toString();
    }

    boolean isPalindropme(String s) {
        char[] cc = s.toCharArray();
        for (int i=0; i< s.length() / 2; i++) {
            if (cc[i] != cc[s.length() - i - 1]) {
                return false;
            }
        }
        return true;
    }

}
