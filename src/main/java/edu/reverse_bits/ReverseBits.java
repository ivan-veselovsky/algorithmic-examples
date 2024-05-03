package edu.reverse_bits;

public class ReverseBits {
    public int reverseBits(final int n) {
        int r = n;
        for (int i=0; i<16; i++) {
            boolean x = getBit(r, i);
            boolean y = getBit(r, 31 - i);
            if (x != y) {
                r = setBit(r, x, 31 - i);
                r = setBit(r, y, i);
            }
        }
        return r;
    }

    boolean getBit(int n, int bit) {
        return (n & (1 << bit)) != 0;
    }

    int setBit(int n, boolean bitValue, int bit) {
        if (bitValue) {
            return n | (1 << bit);
        } else {
            return n & ~(1 << bit);
        }
    }
}
