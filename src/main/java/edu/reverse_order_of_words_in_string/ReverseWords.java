package edu.reverse_order_of_words_in_string;

public class ReverseWords {
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        int y = s.length() - 1;
        while (true) {
            while (y >= 0 && s.charAt(y) == ' ') {
                y--;
            }
            if (y < 0) {
                break;
            }
            int x = y; // last char of the word
            while (x >= 0 && s.charAt(x) != ' ') {
                x--; // before 1st char of the word
            }
            String word = s.substring(x + 1, y + 1);
            sb.append(word).append(' ');
            if (x <= 0) {
                break;
            }
            y = x;
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

}
