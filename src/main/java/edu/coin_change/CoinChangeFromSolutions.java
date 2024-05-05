package edu.coin_change;

import java.util.Arrays;

import static java.lang.Math.min;

/** Solution ported from C++ */
public class CoinChangeFromSolutions {
    public int coinChange(final int[] coins, final int amount) {
        Arrays.sort(coins);

        final int[] minNumbersOfCoins = new int[amount + 1];
        for (int subSum = 1; subSum <= amount; subSum++) {
            minNumbersOfCoins[subSum] = Integer.MAX_VALUE;
            for (int coin: coins) {
                int rootIndex = subSum - coin;
                if (rootIndex < 0) {
                    break;
                }
                if (minNumbersOfCoins[rootIndex] != Integer.MAX_VALUE) {
                    minNumbersOfCoins[subSum] = min(
                        minNumbersOfCoins[subSum],
                            1 + minNumbersOfCoins[rootIndex]);
                }
            }
        }
        int result = minNumbersOfCoins[amount];
        return (result == Integer.MAX_VALUE) ? -1 : result;
    }
}
