package edu.maximal_square;

import java.util.Arrays;

public class MaxSquare {

    public int maximalSquare(final char[][] matrix) {
        final int maxSquareLimit = Math.min(matrix.length , matrix[0].length);
        VerticalProcessor verticalProcessor = new VerticalProcessor(matrix[0].length, matrix.length, maxSquareLimit);
        int maxSquare = 0;
        for (int x=0; x<matrix[0].length; x++) {
            verticalProcessor.stepRight(x, matrix);
            maxSquare = verticalProcessor.renewVerticalStatistics();
            if (maxSquare == maxSquareLimit) {
                break;
            }
        }

        return maxSquare * maxSquare;
    }

    static class VerticalProcessor {
        final ConsequentHitCounter[] horizontalCounters;
        final int horizontalSize;
        final int verticalSize;
        final int maxSquareLimit;
        int maxSquare;
        int x = -1;
        VerticalProcessor(int horizontalSize, int verticalSize, int maxSquareLimit) {
            this.horizontalSize = horizontalSize;
            this.verticalSize = verticalSize;
            this.horizontalCounters = new ConsequentHitCounter[verticalSize];
            this.maxSquareLimit = maxSquareLimit;
            for (int i = 0; i< horizontalCounters.length; i++) {
                horizontalCounters[i] = new ConsequentHitCounter(maxSquareLimit);
            }
        }
        void stepRight(int x0, char[][] matrix) {
            x++;
            assert x == x0;
            for (int y = 0; y< horizontalCounters.length; y++) {
                ConsequentHitCounter counter = horizontalCounters[y];
                counter.add(getBool(x, y, matrix));
            }
        }
        private int renewVerticalStatistics() {
            int max = getMaxSquare();
            if (max > maxSquare) {
                maxSquare = max;
            }
            return maxSquare;
        }
        int getMaxSquare() {
            final int maxCount = Arrays.stream(horizontalCounters).mapToInt(ConsequentHitCounter::getCount).max().getAsInt();
            final int potentialSquareSize = potentialSquareSize(maxCount);
            final int numVerticalCounters = potentialSquareSize - maxSquare;
            if (numVerticalCounters == 0) {
                return maxSquare;
            }
            for (int i = 0; i < numVerticalCounters; i++) {
                final int targetSquareSize = potentialSquareSize - i;
                ConsequentHitCounter verticalCounter = new ConsequentHitCounter(targetSquareSize);
                for (ConsequentHitCounter horizontalCounter: horizontalCounters) {
                    verticalCounter.add(horizontalCounter.getCount() >= targetSquareSize);
                    if (verticalCounter.getCount() >= targetSquareSize) {
                        return targetSquareSize;
                    }
                }
            }
            return maxSquare;
        }
        int potentialSquareSize(int maxHorizontalCount) {
            // min: x + 1, maxSquareLimit, maxCount;
            // x == 0 => size = 1:
            int m1 = Math.min(x + 1, maxSquareLimit);
            return Math.min(m1, maxHorizontalCount);
        }
    }

    public static class ConsequentHitCounter {
        final int windowLength;
        int numberOfConsequentHits;
        int maxNumberOfConsequentHits;
        ConsequentHitCounter(int len) {
            this.windowLength = len;
        }
        void add(boolean value) {
            if (value) {
                if (numberOfConsequentHits < windowLength) {
                    numberOfConsequentHits++;
                }
                assert numberOfConsequentHits <= windowLength;
                if (numberOfConsequentHits > maxNumberOfConsequentHits) {
                    maxNumberOfConsequentHits = numberOfConsequentHits;
                }
            } else {
                numberOfConsequentHits = 0;
            }
        }
        int getCount() {
            return numberOfConsequentHits;
        }
        int getMaxCount() {
            return maxNumberOfConsequentHits;
        }
    }

    static boolean getBool(int x, int y, char[][] matrix) {
        return (matrix[y][x] == '1');
    }
}
