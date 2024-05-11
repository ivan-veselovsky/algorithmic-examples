package edu.common;

public class MathUtils {

    public static int powerOf2(int pow) {
        return (1 << pow);
    }

    public static int maxContainedPowerOf2(int x) {
        int cnt = -1;
        while (x != 0) {
            x >>>= 1;
            cnt++;
        }
        return cnt;
    }

    public static int sign(long x) {
        if (x == 0) {
            return 0;
        } else {
            return (x > 0L) ? 1 : -1;
        }
    }
}
