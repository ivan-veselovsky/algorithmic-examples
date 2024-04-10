package edu.merge_sorted_arrays;

public class MergeSortedArrays {

    public void merge(int[] xx, int m, int[] yy, int n) {
        assert xx.length == m + n;
        assert yy.length == n;
        if (n == 0) {
            return;
        }

        int readX = m - 1; //
        int readY = n - 1; //
        int writeX = m + n - 1; // last in xx.
        while (true) {
            boolean takeFromX = (m > 0) && (readX >= 0 && xx[readX] >= yy[readY]);
            if (takeFromX) {
                xx[writeX] = xx[readX];
                readX--;
            } else {
                xx[writeX] = yy[readY];
                readY--;
            }
            writeX--;
            if (writeX < 0 || writeX == readX || readY < 0) {
                break;
            }
        }
    }

}
