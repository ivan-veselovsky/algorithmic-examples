package edu.array_rotation;

public class ArrayRotation {
    private int[] nums;
    private int buf;

    public void rotate(int[] nums0, int k0) {
        nums = nums0;
        final int len = nums.length;
        int k = k0;
        if (k >= len) {
            k %= len; // mod len, k >= len does not make sense.
        }
        if (k == 0) {
            return; // nothing to do
        }

        final int lcm = least_common_multiple(len, k);
         if (lcm < len * k) {
            final int exchangesInLoop = lcm / k;
            assert len % exchangesInLoop == 0;
            final int loops = len / exchangesInLoop;
            for (int i = 0; i < loops; i++) {
                buf = nums[i];
                for (int e=0; e<exchangesInLoop; e++) {
                    int targetIndex = (i + (e + 1) * k) % len;
                    exchangeWithBuf(targetIndex);
                }
            }
        } else {
            buf = nums[0];
            for (int e=0; e<len; e++) {
                int targetIndex = (k * (e + 1)) % len;
                exchangeWithBuf(targetIndex);
            }
        }
    }

    private int least_common_multiple(int x, int y) {
        assert y < x;
        for (int i=1; i < y; i++) {
            if ((x * i) % y == 0) {
                return x * i;
            }
        }
        return x * y;
    }

    private void exchangeWithBuf(int targetIndex) {
        assert targetIndex >= 0;
        assert targetIndex < nums.length;

        int targetValue = nums[targetIndex];
        nums[targetIndex] = buf;
        buf = targetValue;
    }
}
