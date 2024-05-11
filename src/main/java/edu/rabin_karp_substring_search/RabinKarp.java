package edu.rabin_karp_substring_search;

import com.google.common.base.Preconditions;
import edu.common.ModularArithmetic;
import lombok.Getter;

import java.util.function.IntUnaryOperator;

@Getter
public class RabinKarp {
    // actually it should be the largest prime, such that q * alphabetModule < Long.MAX_VALUE
    public static final long q_26 = 354745078340568241L;

    private final long q;
    private final ModularArithmetic mod;
    private final int alphabetSize; // d
    private final long alphabetSizeInverse;
    private final int[] textValues;

    private long value;
    private long h; // d ^ (m-1) % q , where m is the pattern length
    private int headInclusive;
    private int tailExclusive;

    static int[] makeTextValues(String text, IntUnaryOperator valueProvider) {
        int[] textValues = new int[text.length()];
        for (int i = 0; i < textValues.length; i++) {
            textValues[i] = valueProvider.applyAsInt(text.charAt(i));
        }
        return textValues;
    }

    public RabinKarp(long q, int alphabetSize, int[] textValues) {
        Preconditions.checkArgument(alphabetSize > 0);
        Preconditions.checkArgument(q > alphabetSize);
        this.q = q;
        this.alphabetSize = alphabetSize;
        this.mod = new ModularArithmetic(q);
        long inv = mod.moduloInverse(alphabetSize);
        Preconditions.checkState(inv > 0L, "The alphabet size " + alphabetSize
                + "Must be inversible by modulo " + q);
        assert inv < q;
        assert mod.isModuloInverse(alphabetSize, inv);
        this.alphabetSizeInverse = inv;
        this.textValues = textValues;
    }

    public RabinKarp compute(final int headInclusive, final int tailExclusive) {
        Preconditions.checkArgument(tailExclusive >= headInclusive);
        this.headInclusive = headInclusive;
        this.tailExclusive = headInclusive;
        value = 0;
        h = 0;
        if (tailExclusive > headInclusive) {
            for (int i = 0; i < (tailExclusive - headInclusive); i++) {
                if (i == 0) {
                    h = 1;
                    value = textValues[headInclusive];
                    this.tailExclusive = headInclusive + 1;
                } else {
                    extendTail();
                }
            }
        }
        assert checkInvariants();
        return this;
    }

    public int length() { // m
        return tailExclusive - headInclusive;
    }

    public void shiftToTail() {
        int headValue = textValues[headInclusive];
        headInclusive++;
        tailExclusive++;
        int newTailValue = textValues[tailExclusive - 1];

        final long v0 = value;
        long prod1 = mod.prod(headValue, h);
        long diff = mod.subtract(value, prod1);
        long prod2 = mod.prod(alphabetSize, diff);
        value = mod.sum(prod2, newTailValue);
//        long v_check = mod.mod(alphabetSize * (v0 - headValue * h) + newTailValue);
//        assert v_check == value;

        assert checkInvariants();
    }

    public void shiftToHead() {
        int tailValue = textValues[tailExclusive - 1];
        tailExclusive--;
        headInclusive--;
        int newHeadValue = textValues[headInclusive];

        long prod_new_head_h = mod.prod(newHeadValue, h);
        if (mod.exact()) {
            value = mod.sum((value - tailValue) / alphabetSize, prod_new_head_h);
        } else {
            long sub1 = mod.subtract(value, tailValue);
            long prod2 = mod.prod(sub1, alphabetSizeInverse);
            value = mod.sum(prod2, prod_new_head_h);
        }
        assert checkInvariants();
    }

    public void extendHead() {
        headInclusive--;
        int newHeadValue = textValues[headInclusive];

        increasePowerOfH();
        value = mod.sum(value, mod.prod(newHeadValue, h));

        assert checkInvariants();
    }

    public void extendTail() {
        tailExclusive++;
        int newTailValue = textValues[tailExclusive - 1];

        value = mod.sum(
                mod.prod(alphabetSize, value), newTailValue);
        increasePowerOfH();

        assert checkInvariants();
    }

    private void increasePowerOfH() {
        h = mod.prod(h, alphabetSize);
    }

    public boolean exact() {
        return mod.exact();
    }

    private boolean checkInvariants() {
        return value >= 0L
                && value < mod.modulo()
                && headInclusive >= 0
                && headInclusive < textValues.length
                && tailExclusive >= 0
                && tailExclusive <= textValues.length
                && tailExclusive >= headInclusive
                && h > 0;
    }
}
