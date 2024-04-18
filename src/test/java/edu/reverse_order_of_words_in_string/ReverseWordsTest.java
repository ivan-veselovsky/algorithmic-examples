package edu.reverse_order_of_words_in_string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class ReverseWordsTest {

    @Test
    void reverseWords1() {
        then(new ReverseWords().reverseWords("the sky is blue")).isEqualTo("blue is sky the");
    }

    @Test
    void reverseWords2() {
        then(new ReverseWords().reverseWords("  hello world  ")).isEqualTo("world hello");
    }

    @Test
    void reverseWords3() {
        then(new ReverseWords().reverseWords("a good   example")).isEqualTo("example good a");
    }
}