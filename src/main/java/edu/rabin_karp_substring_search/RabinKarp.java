package edu.rabin_karp_substring_search;

import com.google.common.base.Preconditions;
import edu.common.ModularArithmetic;
import lombok.Getter;

import java.util.function.IntUnaryOperator;

@Getter
public class RabinKarp {
    // actually it should be the largest prime, such that q * alphabetModule < Long.MAX_VALUE
    public static final long q_26 = 354745078340568241L;

    private final ModularArithmetic mod;
    private final int alphabetSize; // d
    private final long inverseAlphabetSize;
    private final int[] textValues;

    private long value;
    private long h; // d ^ (m-1) % modulo , where m is the pattern length
    private int headInclusive;
    private int tailExclusive;

    public static int[] makeTextValues(String text, IntUnaryOperator valueProvider) {
        int[] textValues = new int[text.length()];
        for (int i = 0; i < textValues.length; i++) {
            textValues[i] = valueProvider.applyAsInt(text.charAt(i));
        }
        return textValues;
    }

    public RabinKarp(long modulo, int alphabetSize, int[] textValues) {
        Preconditions.checkArgument(alphabetSize > 0);
        Preconditions.checkArgument(modulo > alphabetSize);
        this.alphabetSize = alphabetSize;
        this.mod = new ModularArithmetic(modulo);
        long inv = mod.moduloInverse(alphabetSize);
        Preconditions.checkState(inv > 0L, "The alphabet size " + alphabetSize
                + "Must be inversible by modulo " + modulo);
        assert inv < modulo;
        assert mod.isModuloInverse(alphabetSize, inv);
        this.inverseAlphabetSize = inv;
        this.textValues = textValues;
    }

    public RabinKarp compute(final int headInclusive, final int tailExclusive) {
        Preconditions.checkArgument(tailExclusive >= headInclusive);
        this.headInclusive = headInclusive;
        this.tailExclusive = headInclusive;
        value = 0;
        h = 0;
        for (int i = 0; i < (tailExclusive - headInclusive); i++) {
            extendTail();
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

        long prod1 = mod.prod(headValue, h);
        long diff = mod.subtract(value, prod1);
        long prod2 = mod.prod(alphabetSize, diff);
        value = mod.sum(prod2, newTailValue);

        assert checkInvariants();
    }

    public void shiftToHead() {
        int tailValue = textValues[tailExclusive - 1];
        tailExclusive--;
        headInclusive--;
        int newHeadValue = textValues[headInclusive];

        long prod_new_head_h = mod.prod(newHeadValue, h);
        if (mod.modCount() == 0) {
            value = mod.sum((value - tailValue) / alphabetSize, prod_new_head_h);
        } else {
            long sub1 = mod.subtract(value, tailValue);
            long prod2 = mod.prod(sub1, inverseAlphabetSize);
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
        if (h == 0L) {
            h = 1L;
        } else {
            h = mod.prod(h, alphabetSize);
        }
    }

    /** TODO: need this method? */
    public boolean exact() {
        return mod.modCount() == 0;
    }

    /**
     * 1 == hit,
     * 0 == does not match,
     * -1 == spurious hit.
     */
    public int maybeMatch(RabinKarp another) {
        if (value == another.value()) {
            if (isRealHit(another)) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    private boolean isRealHit(RabinKarp another) {
        if (length() != another.length()) {
            return false;
        }
        int[] anotherValues = another.textValues();
        int anotherHead = another.headInclusive();
        for (int i = 0; i < length(); i++) {
            if (textValues[headInclusive + i] != anotherValues[anotherHead + i]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkInvariants() {
        return value >= 0L
                && value < mod.modulo()
                && headInclusive >= 0
                && headInclusive <= textValues.length // head inclusive can be == length before extendHead
                && tailExclusive >= 0
                && tailExclusive <= textValues.length
                && tailExclusive >= headInclusive
                && (((length() == 0) && h == 0) || ((length() > 0) && h > 0));
    }
}
