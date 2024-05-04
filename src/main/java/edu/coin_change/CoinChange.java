package edu.coin_change;

import edu.common.RedBallQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;

public class CoinChange {
    private int[] coins;
    private int amount;

    /** BFS-based solution, seems to be not the best. */
    public int coinChange(final int[] coins, final int amount) {
        if (amount == 0) {
            return 0;
        }
        Arrays.sort(coins);
        this.coins = coins;
        this.amount = amount;

        return bfs(0);
    }

    private int bfs(final int start) {
        final BitSet reachedSums = new BitSet(amount);
        final RedBallQueue<Integer> queue = new RedBallQueue<>(new ArrayDeque<>());
        queue.enqueue(start);

        int coinCount = 0;
        while (!queue.isEmpty()) {
            final int sum = queue.dequeue();
            if (sum == amount) {
                return coinCount;
            }
            if (queue.redBallPosition() == 0) {
                coinCount++;
            }

            if (!reachedSums.get(sum)) {
                reachedSums.set(sum);

                for (int coin : coins) {
                    int sum2 = sum + coin;
                    if (sum2 <= amount) {
                        queue.enqueue(sum2);
                    }
                }
            }
        }

        return -1;
    }

}
