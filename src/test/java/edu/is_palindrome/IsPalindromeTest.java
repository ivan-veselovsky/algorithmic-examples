package edu.is_palindrome;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class IsPalindromeTest {

    @Test
    void test1 () {
        boolean actual = new IsPalindrome().isPalindrome("A man, a plan, a canal: Panama");
        then(actual).isTrue();
    }

    @Test
    void test2 () {
        boolean actual = new IsPalindrome().isPalindrome("race a car");
        then(actual).isFalse();
    }

}