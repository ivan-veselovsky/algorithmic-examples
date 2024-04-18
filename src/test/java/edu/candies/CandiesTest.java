package edu.candies;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class CandiesTest {

    @Test
    void case1() {
        int[] ratings = new int[] {1,0,2};
        then(new Candies().candy(ratings)).isEqualTo(5);
    }

    @Test
    void case2() {
        int[] ratings = new int[] {1,2,2};
        then(new Candies().candy(ratings)).isEqualTo(4);
    }

    @Test
    void case11() {
        int[] ratings = new int[] {0, 1, 5, 3, 2, 1};
        then(new Candies().candy(ratings)).isEqualTo(13);
    }


    @Test
    void case3() {
        int[] ratings = new int[] {1,3,2,2,1};
        then(new Candies().candy(ratings)).isEqualTo(7);
    }
}