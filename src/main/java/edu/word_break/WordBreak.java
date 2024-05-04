package edu.word_break;

import java.util.*;

public class WordBreak {
    private String string;
    private Dictionary dictionary;
    int minLength;
    int maxLength;

    public boolean wordBreak(final String string, final List<String> wordDict) {
        this.dictionary = new Dictionary(wordDict);
        this.string = string;
        minLength = dictionary.minWordLength();
        maxLength = dictionary.maxWordLength();

        BitSet bitSet = new BitSet(string.length());

        return bfs(0, bitSet);
    }

    private boolean bfs(final int start, final BitSet reachedNodes) {
        final Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            Integer index = queue.poll();
            if (!reachedNodes.get(index)) {
                reachedNodes.set(index);

                for (String matching : getMatchingWords(index)) {
                    int index2 = index + matching.length();
                    if (index2 == string.length()) {
                        return true; // finish reached.
                    } else {
                        queue.offer(index2);
                    }
                }
            }
        }

        return false;
    }

    private Set<String> getMatchingWords(int index) {
        NavigableSet<String> result = new TreeSet<>();
        NavigableSet<String> set = null;
        for (int len = minLength; len <= maxLength; len++) {
            String prefix = getSubstring(index, len);
            set = dictionary.rangeForPrefix(set, prefix);
            if (set.contains(prefix)) {
                result.add(prefix);
            }
        }
        return result;
    }

    private String getSubstring(int index, int length) {
        return string.substring(index, Math.min(index + length, string.length()));
    }

    // TODO: later change it to Trie.
    static class Dictionary {
        private final NavigableSet<String> set = new TreeSet<>();
        private final int minWordLength;
        private final int maxWordLength;

        Dictionary(List<String> dict) {
            set.addAll(dict);
            IntSummaryStatistics summaryStatistics = dict.stream().mapToInt(String::length).summaryStatistics();
            minWordLength = summaryStatistics.getMin();
            maxWordLength = summaryStatistics.getMax();
        }

        NavigableSet<String> rangeForPrefix(String prefix) {
            return rangeForPrefix(set, prefix);
        }

        NavigableSet<String> rangeForPrefix(NavigableSet<String> subSet, String prefix) {
            if (subSet == null) {
                return rangeForPrefix(prefix);
            }
            return subSet.subSet(prefix, true,
                    prefix + '\uffff', false);
        }
        public NavigableSet<String> set() {
            return this.set;
        }
        public int minWordLength() {
            return this.minWordLength;
        }
        public int maxWordLength() {
            return this.maxWordLength;
        }
    }
}
