package edu.maximal_square;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MaxSquareTest {

    @Test
    void case1() {
        char[][] matrix = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(4);
    }

    @Test
    void case2() {
        char[][] matrix = {
                {'1','0','1','0','0'},
                {'1','1','1','1','1'},
                {'1','1','1','1','1'},
                {'1','1','1','1','0'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(9);
    }

    @Test
    void case4() {
        char[][] matrix = {
                {'1','0','1','0','0'},
                {'0','1','1','1','1'},
                {'1','1','1','1','1'},
                {'0','1','1','1','1'},
                {'1','1','1','1','1'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(16);
    }

    @Test
    void case5() {
        char[][] matrix = {
                {'0','1'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(1);
    }

    @Test
    void case6() {
        char[][] matrix = {
                {'0'}, {'1'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(1);
    }
    
    @Test
    void test7() {
        char[][] matrix = {
                {'1','0','1','0'},
                {'1','0','1','1'},
                {'1','0','1','1'},
                {'1','1','1','1'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(4);
    }
    
    @Test
    void test8() {
        char[][] matrix = {
                {'0','0','0','0','1','1','1','0','1'},
                {'0','0','1','1','1','1','1','0','1'},
                {'0','0','0','1','1','1','1','1','0'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(9);
    }

    @Test
    void case9() {
        char[][] matrix = {
                {'1','0','1','0','0'},
                {'0','0','1','1','1'},
                {'1','1','1','1','1'},
                {'0','1','1','1','1'},
                {'1','1','1','1','1'}
        };
        int max = new MaxSquare().maximalSquare(matrix);
        then(max).isEqualTo(9);
    }


    @Test
    void maximalSquare() {
        MaxSquare.ConsequentHitCounter counter = new MaxSquare.ConsequentHitCounter(3);
        counter.add(true);
        then(counter.getCount()).isEqualTo(1);
        then(counter.getMaxCount()).isEqualTo(1);
        counter.add(true);
        then(counter.getCount()).isEqualTo(2);
        then(counter.getMaxCount()).isEqualTo(2);
        counter.add(false);
        then(counter.getCount()).isEqualTo(0);
        then(counter.getMaxCount()).isEqualTo(2);
        counter.add(true);
        counter.add(true);
        counter.add(true);
        then(counter.getCount()).isEqualTo(3);
        then(counter.getMaxCount()).isEqualTo(3);
        counter.add(true);
        then(counter.getCount()).isEqualTo(3);
        then(counter.getMaxCount()).isEqualTo(3);
        counter.add(false);
        then(counter.getCount()).isEqualTo(0);
        then(counter.getMaxCount()).isEqualTo(3);
    }
}