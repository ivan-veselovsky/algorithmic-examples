package edu.is_anagram;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class IsAnagramTest {

    @Test
    void case1() {
        then(new IsAnagram().isAnagram("anagram", "nagaram")).isTrue();
    }

    @Test
    void case2() {
        then(new IsAnagram().isAnagram("car", "rat")).isFalse();
    }
}