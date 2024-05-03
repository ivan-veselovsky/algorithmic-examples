package edu.hamming_weight;

public class BitCount {
    public int hammingWeight(int n) {
        int bitCount = 0;
        while (n != 0) {
            if ((n & 1) != 0) {
                bitCount++;
            }
            n >>>= 1;
        }
        return bitCount;
    }
}
