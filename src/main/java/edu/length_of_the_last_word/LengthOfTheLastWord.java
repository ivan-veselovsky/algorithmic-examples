package edu.length_of_the_last_word;

public class LengthOfTheLastWord {

    public int lengthOfLastWord(String s) {
        char[] chars = s.toCharArray();
        int i = chars.length - 1;
        while (i >= 0 && chars[i] == ' ') {
            i--;
        }
        if (i < 0) {
            return 0;
        }
        int lastCharOfTheLastWord = i;
        while (i >= 0 && chars[i] != ' ') {
            i--;
        }
        int firstCharOfTheLastWord = i;
        return (lastCharOfTheLastWord - firstCharOfTheLastWord);
    }

}
