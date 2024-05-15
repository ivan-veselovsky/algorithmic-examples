package edu.rabin_karp_substring_search;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntUnaryOperator;

import static edu.longest_palindromic_substring.LongestPalindromicSubstring.maxAlphabetSize;
import static edu.rabin_karp_substring_search.RabinKarp.q_26;
import static edu.rabin_karp_substring_search.RabinKarp.q_62;
import static org.assertj.core.api.BDDAssertions.then;

class RabinKarpTest {

    final String textStr = "235902314152673992199831415876031415";
    final String patternStr = "31415";
    final IntUnaryOperator valueProvider = x -> x - '0';

    @Test
    void shift_to_tail_exact() {
        int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(q_26,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.value()).isEqualTo(31415L);

        int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(q_26,10, textValues)
                .compute(0, patternStr.length());
        then(rabinKarpText.value()).isEqualTo(23590L);

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = 0; i <= (textStr.length() - patternStr.length()); i++) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0L).isLessThan(rabinKarpPattern.mod().modulo());
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(35902L);
            }
            if (i < (textStr.length() - patternStr.length())) {
                boolean shifted = rabinKarpText.shiftToTail();
                then(shifted).isTrue();
            }
        }

        then(rabinKarpText.shiftToTail()).isFalse();
        then(matches).containsExactly(6, 22, 31);
        then(rabinKarpText.exact()).isTrue();
        then(rabinKarpPattern.exact()).isTrue();
        then(spuriousHitCount).isZero();
    }

    @Test
    void shift_to_head_exact() {
        final int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(q_26,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.value()).isEqualTo(31415L);
        then(rabinKarpPattern.length()).isEqualTo(5);

        final int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(q_26,10, textValues)
                .compute(textStr.length() - patternStr.length(), textStr.length());
        then(rabinKarpText.value()).isEqualTo(31415L);
        then(rabinKarpText.length()).isEqualTo(5);

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = (textStr.length() - patternStr.length()); i >= 0; i--) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0).isLessThan(rabinKarpPattern.mod().modulo());
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            if (i == 0) {
                then(rabinKarpText.value()).isEqualTo(23590L);
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(35902L);
            }
            if (i > 0) {
                then(rabinKarpText.shiftToHead()).isTrue();
            }
        }
        then(rabinKarpText.shiftToHead()).isFalse();
        then(matches).containsExactly(6, 22, 31);
        then(rabinKarpText.exact()).isTrue();
        then(rabinKarpPattern.exact()).isTrue();
        then(spuriousHitCount).isZero();
    }

    @Test
    void shift_to_head_exact_extend_head() {
        final int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(q_26,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.value()).isEqualTo(31415L);
        then(rabinKarpPattern.shiftToHead()).isFalse();
        then(rabinKarpPattern.shiftToTail()).isFalse();

        final int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(q_26,10, textValues)
                .compute(textStr.length(), textStr.length());
        then(rabinKarpText.value()).isEqualTo(0L);
        for (int i = 0; i < patternStr.length(); i++) {
            rabinKarpText.extendHead();
        }
        then(rabinKarpText.value()).isEqualTo(31415L);

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = (textStr.length() - patternStr.length()); i >= 0; i--) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0).isLessThan(rabinKarpPattern.mod().modulo());
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            if (i == 0) {
                then(rabinKarpText.value()).isEqualTo(23590L);
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(35902L);
            }
            if (i > 0) {
                then(rabinKarpText.shiftToHead()).isTrue();
            }
        }
        then(rabinKarpText.shiftToHead()).isFalse();
        then(matches).containsExactly(6, 22, 31);
        then(rabinKarpText.exact()).isTrue();
        then(rabinKarpPattern.exact()).isTrue();
        then(spuriousHitCount).isZero();
    }


    @Test
    void shift_to_tail_moduled() {
        final long q = 13;

        int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(q,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.exact()).isFalse();
        final long patternValue = rabinKarpPattern.value();
        then(patternValue).isEqualTo(7L);

        int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(q,10, textValues)
                .compute(0, patternStr.length());
        then(rabinKarpText.exact()).isFalse();
        then(rabinKarpText.value()).isEqualTo(8L);

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = 0; i <= (textStr.length() - patternStr.length()); i++) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0).isLessThan(q);
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(9L);
            }
            if (i < (textStr.length() - patternStr.length())) {
                then(rabinKarpText.shiftToTail()).isTrue();
            }
        }

        then(matches).containsExactly(6, 22, 31);
        then(spuriousHitCount).isEqualTo(3);
        then(rabinKarpText.shiftToTail()).isFalse();
    }

    @Test
    void shift_to_head_moduled() {
        final long q = 13;

        int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(q,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.exact()).isFalse();
        final long patternValue = rabinKarpPattern.value();
        then(patternValue).isEqualTo(7L);

        int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(q,10, textValues)
                .compute(textStr.length() - patternStr.length(), textStr.length());
        then(rabinKarpText.exact()).isFalse();
        then(rabinKarpText.value()).isEqualTo(7L);

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = (textStr.length() - patternStr.length()); i >= 0; i--) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0L).isLessThan(q);
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(9L);
            }
            if (i > 0) {
                then(rabinKarpText.shiftToHead()).isTrue();
            }
        }

        then(rabinKarpText.shiftToHead()).isFalse();
        then(matches).containsExactly(6, 22, 31);
        then(spuriousHitCount).isEqualTo(3);
    }

    @Test
    void shift_to_tail_moduled_little_endian() {
        final long q = 13;

        int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(false, q,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.exact()).isFalse();
        final long patternValue = rabinKarpPattern.value();
        then(patternValue).isEqualTo(11L);

        int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(false, q,10, textValues)
                .compute(0, patternStr.length());
        then(rabinKarpText.exact()).isFalse();
        then(rabinKarpText.value()).isEqualTo(3L);

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = 0; i <= (textStr.length() - patternStr.length()); i++) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0).isLessThan(q);
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(10L);
            }
            if (i < (textStr.length() - patternStr.length())) {
                then(rabinKarpText.shiftToTail()).isTrue();
            }
        }

        then(matches).containsExactly(6, 22, 31);
        then(spuriousHitCount).isEqualTo(3);
        then(rabinKarpText.shiftToTail()).isFalse();
    }

    @Disabled("TODO: fix it!")
    @Test
    void bug_with_long_string() {
        final String text = "321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123210012321001232100123210123";
        final String pattern = "321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123";

        final IntSummaryStatistics textStat = text.chars().summaryStatistics();
        final int alphabetSize = textStat.getMax() - textStat.getMin() + 1;
        // bug happens with alphabet size from 4 to 62
        assert alphabetSize > 0 && alphabetSize <= maxAlphabetSize;
        final int min = textStat.getMin();
        final int[] textValues = RabinKarp.makeTextValues(text, ch -> ch - min);
        final int[] patternValues = RabinKarp.makeTextValues(pattern, ch -> ch - min);

        final long q = q_62; // q_26, q_62, 643 -- reproduces the bug.
        final boolean bigEndian = true; // bug happens with both true and false.

        final RabinKarp rabinKarpPattern = new RabinKarp(bigEndian, q, alphabetSize, patternValues)
                .compute(0, pattern.length());
        final RabinKarp rabinKarpText = new RabinKarp(bigEndian, q, alphabetSize, textValues)
                .compute(text.length() - pattern.length(), pattern.length());

        final Set<Integer> matches = new TreeSet<>();
        int spuriousHitCount = 0;
        for (int i = (text.length() - pattern.length()); i >= 0; i--) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThanOrEqualTo(0).isLessThan(q);
            switch (rabinKarpPattern.maybeMatch(rabinKarpText)) {
                case 1 -> matches.add(i);
                case -1 -> spuriousHitCount++;
            }
            System.out.println(i);
            if (i > 0) {
                then(rabinKarpText.shiftToHead()).isTrue();
            }
        }

        System.out.println("Text Mod count: " + rabinKarpText.mod().modCount());
        System.out.println("Pattern Mod count: " + rabinKarpPattern.mod().modCount());

        System.out.println("Matches: " + matches);
        System.out.println("Spurious hits: " + spuriousHitCount);

        then(matches).containsExactly(0);
        then(spuriousHitCount).isEqualTo(0);
        then(rabinKarpText.shiftToTail()).isFalse();

        then(text.indexOf(pattern, 0)).isEqualTo(0);
        then(text.indexOf(pattern, 1)).isEqualTo(-1);
    }

}