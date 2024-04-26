package edu.happy_number;

public class HappyNumber {

    public boolean isHappy(final int n) {
        long x = n;
        long fast = n;
        while (true) {
            x = quadratize(x);
            if (x == 1) {
                return true;
            }
            fast = quadratize(quadratize(fast));
            if (x == fast) {
                return false; // loop detected
            }
        }
    }

    private long quadratize(long x) {
        long sum = 0;
        long shifted = x;
        while (true) {
            long d = shifted % 10;
            sum += d * d;

            shifted /= 10;
            if (shifted == 0) {
                break;
            }
        }
        return sum;
    }

}
