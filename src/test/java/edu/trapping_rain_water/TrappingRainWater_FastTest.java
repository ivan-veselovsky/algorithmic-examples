package edu.trapping_rain_water;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class TrappingRainWater_FastTest {

    @Test
    void case1() {
        int[] heights = new int[] {0,1,0,2,1,0,1,3,2,1,2,1};
        int water = new TrappingRainWater_Fast().trap(heights);
        then(water).isEqualTo(6);
    }

    @Test
    void case2() {
        int[] heights = new int[] {4,2,0,3,2,5};
        int water = new TrappingRainWater_Fast().trap(heights);
        then(water).isEqualTo(9);
    }

    @Test
    void case3() {
        int[] heights = new int[] {4,2,0,3,2,4,3,4};
        int water = new TrappingRainWater_Fast().trap(heights);
        then(water).isEqualTo(10);
    }

    @Test
    void case4() {
        int[] heights = new int[] {5,5,1,7,1,1,5,2,7,6};
        int water = new TrappingRainWater_Fast().trap(heights);
        then(water).isEqualTo(23);
    }

    @Test
    void case5() {
        int[] heights = new int[] {9,6,8,8,5,6,3};
        int water = new TrappingRainWater_Fast().trap(heights);
        then(water).isEqualTo(3);
    }

    @Test
    void test6() {
        int[] heights = new int[] {8,2,8,9,0,1,7,7,9};
        int water = new TrappingRainWater_Fast().trap(heights);
        then(water).isEqualTo(27);
    }
}