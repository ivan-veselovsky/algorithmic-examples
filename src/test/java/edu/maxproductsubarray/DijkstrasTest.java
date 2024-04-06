package edu.maxproductsubarray;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class DijkstrasTest {

    @Test
    void single_negative() {
        int[] input = {-2};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(-2);
    }

    @Test
    void single_zero() {
        int[] input = {0};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(0);
    }

    @Test
    void single_positive() {
        int[] input = {2};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(2);
    }

    @Test
    void zero_and_positive() {
        int[] input = {0, 2};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(2);
    }

    @Test
    void basic1() {
        int[] input = {2,3,-2,4};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(6);
    }

    @Test
    void basic2() {
        int[] input = {-2,0,-1};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(0);
    }

    @Test
    void basic3() {
        int[] input = { 2,3, -2, 1, 4,3};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(12);
    }

    @Test
    void basic4() {
        int[] input = { 4, 3, 2};
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(24);
    }

    @Test
    void ones_correctly_excluded() {
        int[] input = { -3,0,1,-2 };
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(1);
    }

    @Test
    void zertoes() {
        int[] input = { 0, -1,2,3,4,-5, 0, -3,0,1,-2 , 0 };
        int result = Solution.maxProduct(input);
        then(result).isEqualTo(120);
    }

}