package edu.h_index;

public class H_Index {

    public int hIndex(int[] citations) {
        final int max = max(citations);

        int[] spectrum = new int[max + 1];
        for (int c: citations) {
            spectrum[c]++;
        }
        for (int i=(spectrum.length-1); i>0; i--) {
            spectrum[i-1] += spectrum[i];
            if (spectrum[i] >= i) {
                return i;
            }
        }
        return 0;
    }

    int max(int[] citations) {
        int max = citations[0];
        for (int c: citations) {
            if (c > max) {
                max = c;
            }
        }
        return max;
    }
}
