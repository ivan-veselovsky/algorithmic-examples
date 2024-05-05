package edu.word_break;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class WordBreakFromSolutionsTest {

    @Test
    void case1() {
        then(new WordBreakFromSolutions().wordBreak("leetcode", List.of("leet", "code"))).isTrue();
    }

    @Test
    void case2() {
        then(new WordBreakFromSolutions().wordBreak("applepenapple", List.of("apple", "pen"))).isTrue();
    }

    @Test
    void case3() {
        then(new WordBreakFromSolutions().wordBreak("abcabcabc", List.of("ab", "bc", "ca", "abca", "a"))).isTrue();
        then(new WordBreakFromSolutions().wordBreak("abcabcabc", List.of("ab", "bc", "ca", "abca"))).isFalse();
    }

    @Test
    void case4() {
        then(new WordBreakFromSolutions().wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab",
                List.of("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"))).isFalse();
    }

    @Test
    void case5 () {
        then(new WordBreakFromSolutions().wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                List.of("aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa","ba"))).isFalse();
    }
}