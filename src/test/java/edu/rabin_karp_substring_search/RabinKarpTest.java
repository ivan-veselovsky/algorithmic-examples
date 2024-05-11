package edu.rabin_karp_substring_search;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntUnaryOperator;

import static edu.rabin_karp_substring_search.RabinKarp.q_26;
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

        final long valuePattern = rabinKarpPattern.value();
        final Set<Integer> matches = new TreeSet<>();
        for (int i = 0; i <= (textStr.length() - patternStr.length()); i++) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThan(-rabinKarpPattern.mod().modulo()).isLessThan(rabinKarpPattern.mod().modulo());
            if ((textValue == valuePattern)
                    && ((rabinKarpText.exact() && rabinKarpPattern.exact())
                        || textStr.startsWith(patternStr, i))) { // check for spurious hit
                    matches.add(i);
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(35902L);
            }
            if (i < (textStr.length() - patternStr.length())) {
                rabinKarpText.shiftToTail();
            }
        }

        then(matches).containsExactly(6, 22, 31);
        then(rabinKarpText.exact()).isTrue();
        then(rabinKarpPattern.exact()).isTrue();
    }

    @Test
    void shift_to_head_exact() {
        final int[] patternValues = RabinKarp.makeTextValues(patternStr, valueProvider);
        RabinKarp rabinKarpPattern = new RabinKarp(q_26,10, patternValues)
                .compute(0, patternStr.length());
        then(rabinKarpPattern.value()).isEqualTo(31415L);

        final int[] textValues = RabinKarp.makeTextValues(textStr, valueProvider);
        RabinKarp rabinKarpText = new RabinKarp(q_26,10, textValues)
                .compute(textStr.length() - patternStr.length(), textStr.length());
        then(rabinKarpText.value()).isEqualTo(31415L);

        final long valuePattern = rabinKarpPattern.value();
        final Set<Integer> matches = new TreeSet<>();
        for (int i = (textStr.length() - patternStr.length()); i >= 0; i--) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThan(-rabinKarpPattern.mod().modulo()).isLessThan(rabinKarpPattern.mod().modulo());
            if ((textValue == valuePattern)
                    && ((rabinKarpText.exact() && rabinKarpPattern.exact())
                        || textStr.startsWith(patternStr, i))) { // check for spurious hit
                matches.add(i);
            }
            if (i == 0) {
                then(rabinKarpText.value()).isEqualTo(23590L);
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(35902L);
            }
            if (i > 0) {
                rabinKarpText.shiftToHead();
            }
        }
        then(matches).containsExactly(6, 22, 31);
        then(rabinKarpText.exact()).isTrue();
        then(rabinKarpPattern.exact()).isTrue();
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
        for (int i = 0; i <= (textStr.length() - patternStr.length()); i++) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThan(-q).isLessThan(q);
            if ((textValue == patternValue)
                    && ((rabinKarpText.exact() && rabinKarpPattern.exact())
                    || textStr.startsWith(patternStr, i))) { // check for spurious hit
                matches.add(i);
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(9L);
            }
            if (i < (textStr.length() - patternStr.length())) {
                rabinKarpText.shiftToTail();
            }
        }

        then(matches).containsExactly(6, 22, 31);
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
        for (int i = (textStr.length() - patternStr.length()); i >= 0; i--) {
            long textValue = rabinKarpText.value();
            then(textValue).isGreaterThan(-q).isLessThan(q);
            if ((textValue == patternValue)
                    && ((rabinKarpText.exact() && rabinKarpPattern.exact())
                    || textStr.startsWith(patternStr, i))) { // check for spurious hit
                matches.add(i);
            }
            if (i == 1) {
                then(rabinKarpText.value()).isEqualTo(9L);
            }
            if (i > 0) {
                rabinKarpText.shiftToHead();
            }
        }

        then(matches).containsExactly(6, 22, 31);
    }
}