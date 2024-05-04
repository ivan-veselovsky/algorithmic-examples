package edu.word_break;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class WordBreakTest {

    @Test
    void case1() {
        then(new WordBreak().wordBreak("leetcode", List.of("leet", "code"))).isTrue();
    }

    @Test
    void case2() {
        then(new WordBreak().wordBreak("applepenapple", List.of("apple", "pen"))).isTrue();
    }

    @Test
    void case3() {
        then(new WordBreak().wordBreak("abcabcabc", List.of("ab", "bc", "ca", "abca", "a"))).isTrue();
        then(new WordBreak().wordBreak("abcabcabc", List.of("ab", "bc", "ca", "abca"))).isFalse();
    }

    @Test
    void case4() {
        then(new WordBreak().wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab",
                List.of("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"))).isFalse();
    }

    @Test
    void case5 () {
        then(new WordBreak().wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            List.of("aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa","ba"))).isFalse();
    }
}