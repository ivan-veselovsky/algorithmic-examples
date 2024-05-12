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

}