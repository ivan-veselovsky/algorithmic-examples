package edu.container_with_most_water;

/**
 * Leetcode #11.
 *
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 *
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 *
 * Return the maximum amount of water a container can store.
 *
 * Notice that you may not slant the container.
 */
public class ContainerWithMostWater {
    private int[] height;

    public int maxArea(int[] height) {
        this.height = height;

        int x = 0;
        int y = height.length - 1;
        int heightX = 0;
        int heightY = 0;
        int maxArea = 0;
        int area;
        while (true) {
            heightX = height[x];
            heightY = height[y];
            if (heightX < heightY) {
                area = (y - x) * heightX;
                x = nextHigherThanBefore(x, heightX, y);
            } else {
                area = (y - x) * heightY;
                y = previousHigherThanAfter(y, heightY, x);
            }
            if (area > maxArea) {
                maxArea = area;
            }
            if (y == -1 || x == -1) {
                break;
            }
        }
        return maxArea;
    }

    private int nextHigherThanBefore(int x, int heightX, int y) {
        for (int i=x; i<y; i++) {
            if (height[i] > heightX) {
                return i;
            }
        }
        return -1;
    }

    private int previousHigherThanAfter(int y, int heightY, int x) {
        for (int i=y; i>x; i--) {
            if (height[i] > heightY) {
                return i;
            }
        }
        return -1;
    }
}
