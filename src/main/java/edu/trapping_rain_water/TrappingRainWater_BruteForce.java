package edu.trapping_rain_water;

import java.util.Arrays;

public class TrappingRainWater_BruteForce {
    private int[] heights;
    private int maxHeight;

    public int trap(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        this.heights = heights;
        this.maxHeight = Arrays.stream(heights).max().getAsInt();

        int waterCount = 0; // global water count
        int firstGround = 0;
        int behindLastGround = heights.length;
        for (int y=1; y <= maxHeight; y++) {
            int groundToAirIndex = -1;
            boolean prevGround = false; // initially we come from air.
            int firstG = -1;
            int lastG = -1;
            //System.out.println("first = " + firstGround + ", behind last = " + behindLastGround);
            for (int x=firstGround; x<behindLastGround; x++) {
                boolean ground = isGround(x, y);
                if (ground) {
                    if (firstG == -1) {
                        firstG = x;
                    }
                    lastG = x;
                }
                if (prevGround ^ ground) {
                    if (prevGround) {
                        // from ground to air:
                        groundToAirIndex = x;
                    } else {
                        // from air to groud:
                        if (groundToAirIndex > -1 && groundToAirIndex < x) {
                            waterCount += (x - groundToAirIndex);
                        }
                        groundToAirIndex = -1;
                    }
                }
                prevGround = ground;
            }
            firstGround = firstG;
            behindLastGround = lastG + 1;
            //System.out.println("h = " + y + ": water = " + waterCount);
        }
        return waterCount;
    }

    boolean isGround(int x, int y) {
        if (y > maxHeight) {
            return false;
        }
        return (heights[x] >= y);
    }
}
