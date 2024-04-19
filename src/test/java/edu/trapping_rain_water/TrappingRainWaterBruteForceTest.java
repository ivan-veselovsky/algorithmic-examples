package edu.trapping_rain_water;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class TrappingRainWaterBruteForceTest {

    @Test
    void case1() {
        int[] heights = new int[] {0,1,0,2,1,0,1,3,2,1,2,1};
        int water = new TrappingRainWater_BruteForce().trap(heights);
        then(water).isEqualTo(6);
    }
}