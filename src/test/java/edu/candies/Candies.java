package edu.candies;

public class Candies {
    private int c;
    private int topValue;

    public int candy(int[] ratings) {
        if (ratings.length == 0) {
            return 0;
        }
        if (ratings.length == 1) {
            return 1;
        }

        int stepCount = 0;
        boolean up = false;
        for (int i=1; i<ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {  // up:
                if (i == 1 || !up) { // down way finished:
                    up = true;
                    addConsideringTopValue(stepCount);
                    stepCount = 1;
                } else {
                    stepCount++;
                }
            } else if (ratings[i] < ratings[i - 1]) {  // down :
                if (i == 1 || up) { // way up finished:
                    up = false;
                    c += stepCount * (stepCount + 1) / 2;
                    topValue = stepCount; // !! remember the top value
                    stepCount = 1;
                } else {
                    stepCount++;
                }
            } else if (ratings[i] == ratings[i - 1]) { // flat shelf:
                addConsideringTopValue(stepCount);
                stepCount = 0;
            } else {
                assert false;
            }
        }

        if (up || topValue == 0) {
            c += stepCount * (stepCount + 1) / 2;
        } else {
            addConsideringTopValue(stepCount);
        }

        return c + ratings.length; // at least 1 for each kid
    }

    private void addConsideringTopValue(int stepCount) {
        if (topValue > stepCount) {
            c += (stepCount  - 1) * stepCount / 2; // skip topmost step
        } else {
            c += stepCount * (stepCount + 1) / 2 - topValue;
        }
        topValue = 0;
    }

}
