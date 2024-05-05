package edu.coin_change;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class CoinChangeFromSolutionsTest {

    @Test
    void case0() {
        int actualCount = new CoinChangeFromSolutions().coinChange(new int[] {1, 2, 5}, 6);
        then(actualCount).isEqualTo(2);
    }

    @Test
    void case1() {
        int actualCount = new CoinChangeFromSolutions().coinChange(new int[] {1, 2, 5}, 11);
        then(actualCount).isEqualTo(3);
    }
    @Test
    void case2() {
        int actualCount = new CoinChangeFromSolutions().coinChange(new int[] {2}, 3);
        then(actualCount).isEqualTo(-1);
    }
    @Test
    void case3() {
        int actualCount = new CoinChangeFromSolutions().coinChange(new int[] {1}, 0);
        then(actualCount).isEqualTo(0);
    }

    @Test
    void case4() {
        int actualCount = new CoinChangeFromSolutions().coinChange(new int[] {186,419,83,408}, 6249);
        then(actualCount).isEqualTo(20);
    }

    @Test
    void case5() {
        int actualCount = new CoinChangeFromSolutions().coinChange(new int[] {3,7,405,436}, 8839);
        then(actualCount).isEqualTo(25);
    }
}