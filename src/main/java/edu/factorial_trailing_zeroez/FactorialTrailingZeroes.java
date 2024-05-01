package edu.factorial_trailing_zeroez;

public class FactorialTrailingZeroes {
    // smart solution from "solutions":
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    // my own solution:
    public int trailingZeroes0(final int n) {
        int count5 = 0;
        for (int i=5; i<=n; i += 5) {
            count5 += getFactorizationPower(i, 5);
        }
        if (count5 == 0) {
            return 0;
        }

        int count2 = 0;
        for (int i=2; i<=n; i += 2) {
            count2 += getFactorizationPower(i, 2);
            if (count2 >= count5) {
                break;
            }
        }

        return Math.min(count2, count5);
    }

    int getFactorizationPower(int x, final int prime) {
        int count = 0;
        while (x % prime == 0) {
            x /= prime;
            count++;
        }
        return count;
    }
}
