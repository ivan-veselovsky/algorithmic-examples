package edu.common;

public class MathUtils {

    public static int power2(int pow) {
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

}
