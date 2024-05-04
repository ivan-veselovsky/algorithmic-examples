package edu.coin_change;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class CoinChangeTest {

    @Test
    void case4() {
        int actualCount = new CoinChange().coinChange(new int[] {186,419,83,408}, 6249);
        then(actualCount).isEqualTo(20);
    }

    @Test
    void case5() {
        int actualCount = new CoinChange().coinChange(new int[] {3,7,405,436}, 8839);
        then(actualCount).isEqualTo(25);
    }
}