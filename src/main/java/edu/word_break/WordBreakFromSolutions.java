package edu.word_break;

import java.util.List;

public class WordBreakFromSolutions {
    private List<String> wordDict;
    private String target;
    private Boolean[] memo;

    public boolean wordBreak(String s, List<String> wordDict) {
        this.wordDict = List.copyOf(wordDict);
        this.target = s;
        this.memo = new Boolean[s.length() + 1];

        return recWay1();
    }

    boolean recWay1() {
        return targetEndReachableFromK(0);
    }

    boolean targetEndReachableFromK(int k) {
        if (k == target.length()) {
            return true; // done.
        }
        if (memo[k] != null) {
            return memo[k]; // cache hit
        }

        boolean reachable = false;
        for (String word: wordDict) {
            if (target.startsWith(word, k)
                    && targetEndReachableFromK(k + word.length())) {
                reachable = true;
                break;
            }
        }

        memo[k] = reachable; // cache

        return reachable;
    }
}
