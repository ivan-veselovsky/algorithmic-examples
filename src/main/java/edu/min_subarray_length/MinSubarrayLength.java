package edu.min_subarray_length;

/**
 * 209. Minimum Size Subarray Sum
 *
 * Given an array of positive integers nums and a positive integer target, return the minimal length of a
 * subarray
 *  whose sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 *
 * Example 1:
 *
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 * Example 2:
 *
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 * Example 3:
 *
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 */
public class MinSubarrayLength {
    public int minSubArrayLen(final int target, int[] nums) {

        final Window window = new Window(target, nums);

        int loopCount = 0;
        while (true) {
            if (!window.eatRightUpToTarget()) {
                if (loopCount == 0) {
                    return 0;
                }
                break;
            }
            //assert window.windowSum() >= target;

            while (window.windowSum >= target) {
                int minLength = window.updateMinLength();
                if (minLength == 1) {
                    return minLength;
                }
                window.dropTail();
            }
            //assert window.windowSum() < target;

            loopCount++;
        }

        return window.minWindowLength;
    }

    static class Window {
        final int target;
        final int[] array;
        int x;
        int y = -1;
        int windowSum;
        int minWindowLength;
        Window(int t, int[] array) {
            //assert t > 0;
            this.target = t;
            //assert array.length > 0;
            this.array = array;
            this.minWindowLength = array.length;
        }
        int windowSum() {
            return windowSum;
        }
        boolean eatRightUpToTarget() {
            while (windowSum < target) {
                y++;
                if (y == array.length) {
                    break;
                }
                windowSum += array[y];
            }
            return (windowSum >= target);
        }
        void dropTail() {
            windowSum -= array[x];
            x++;
        }
        int updateMinLength() {
            int length = y - x + 1;
            if (length < minWindowLength) {
                minWindowLength = length;
            }
            return minWindowLength;
        }
    }
}
