package edu.trapping_rain_water;

import java.util.*;

/**

 // 1. get all max/mins
 //
 // 2. sort by distance
 // 3. sort by value
 //
 // 4. for each min x:
 //      h(x) = min(highestLeftMax, highestRightMax)
 //      dominatorMax(x) = thatOneWhichPossessesThe h(x).
 //
 // 5. group mins by dominators
 //
 // 6. for each group perform integration.
 *
 */
public class TrappingRainWater_Fast {
    private int[] heights;
    private final List<Max> maximumsByDistance = new ArrayList<>(64);
    private final List<Max> minimumsByDistance = new ArrayList<>(64);

    final Comparator<Max> cmpByHeightPreferLeftmost = (m1, m2) -> {
        if (m1 == m2) {
            return 0;
        }
        int diff = m1.value - m2.value;
        if (diff != 0) {
            return diff;
        }
        diff = m2.index - m1.index; // !!!!
        if (diff != 0) {
            return diff;
        }
        throw new IllegalStateException("2 indices cannot be identical");
    };
    final Comparator<Max> cmpByHeightPreferRightmost =  (m1, m2) -> {
        if (m1 == m2) {
            return 0;
        }
        int diff = m1.value - m2.value;
        if (diff != 0) {
            return diff;
        }
        diff = m1.index - m2.index;
        if (diff != 0) {
            return diff;
        }
        throw new IllegalStateException("2 indices cannot be identical");
    };

    void reportMax(int i, int thickness) {
        Max max = new Max(i, heights[i], thickness);
        maximumsByDistance.add(max);
    }

    public int trap(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        this.heights = heights;

        int prevHeight = -1;
        int lastNonZeroDelta = 0;
        int flatCount = 0;
        for (int i=0; i<heights.length; i++) {
            if (i > 0) {
                int delta = (heights[i] - prevHeight);
                if (delta == 0) {
                    flatCount++;
                    if (i == heights.length - 1 && lastNonZeroDelta > 0) {
                        reportMax(i, flatCount + 1); // last flat
                    }
                } else {
                    if (delta > 0) { // up!
                        if (lastNonZeroDelta < 0) { // min detected:
                            minimumsByDistance.add(new Max(i-1, prevHeight, flatCount + 1));
                        }
                        if (i == heights.length - 1) {
                            reportMax(i, 1); // last up, thickness = 1
                        }
                    } else { // down!
                        if (lastNonZeroDelta > 0 ||
                                (lastNonZeroDelta == 0 && (i == 1 || flatCount > 0))) {
                            reportMax(i - 1, flatCount + 1);
                        }
                    }
                    flatCount = 0;
                    lastNonZeroDelta = delta;
                }
            }
            prevHeight = heights[i];
        }

//        System.out.println("Maximums:");
//        maximumsByDistance.forEach(m -> System.out.println(m));
//        System.out.println("Minimums:");
//        minimumsByDistance.forEach(m -> System.out.println(m));

        NavigableSet<Max> leftByHeightSet = new TreeSet<>(cmpByHeightPreferRightmost);
        leftByHeightSet.addAll(maximumsByDistance);
        NavigableSet<Max> rightByHeightSet = new TreeSet<>(cmpByHeightPreferLeftmost);

        Deque<Max> leftByDistanceDeque = new ArrayDeque<>(maximumsByDistance);

        for (int i=minimumsByDistance.size() - 1; i >= 0; i--) {
            computeDominator(minimumsByDistance.get(i),
                    leftByDistanceDeque,
                    leftByHeightSet, rightByHeightSet);
        }
//        System.out.println("==== Minimums with dominators:");
//        minimumsByDistance.forEach(m -> System.out.println(m));

        int water = 0;
        for (Max max: maximumsByDistance) {
            if (max.dominatorOfMinToTheRight) {
                water += integrateToTheRight(max);
            }
            if (max.dominatorOfMinToTheLeft) {
                water += integrateToTheLeft(max);
            }
        }

        return water;
    }

    void computeDominator(final Max min,
              Deque<Max> maximumsByDistance,
              NavigableSet<Max> leftByHeightSet, NavigableSet<Max> rightByHeightSet) {
        while (true) {
            Max rightmostMax = maximumsByDistance.peekLast();
            if (rightmostMax.index > min.index) {
                maximumsByDistance.pollLast();
                rightByHeightSet.add(rightmostMax);
                leftByHeightSet.remove(rightmostMax);
            } else {
                break;
            }
        }

        // TODO: combine this with the integration.
        //   After that the covered (integrated) area can be excluded from further processing,
        //   what will speed up the processing further.
        Max highestRight = rightByHeightSet.last();
        Max highestLeft  = leftByHeightSet.last();

        final Max dominator;
        if (highestLeft.value < highestRight.value) {
            dominator = highestLeft;
            dominator.dominatorOfMinToTheRight = true;
        } else {
            dominator = highestRight;
            dominator.dominatorOfMinToTheLeft = true;
        }
    }

    int integrateToTheLeft(Max dom) {
        int integral = 0;
        int width = 0;
        for (int i=dom.index - dom.width; i>= 0; i--) {
            if (heights[i] < dom.value) {
                integral += heights[i];
                width++;
            } else {
                break;
            }
        }
        return (width * dom.value) - integral;
    }

    int integrateToTheRight(Max dom) {
        int integral = 0;
        int width = 0;
        for (int i=dom.index + 1; i < heights.length; i++) {
            if (heights[i] < dom.value) {
                integral += heights[i];
                width++;
            } else {
                break;
            }
        }
        return (width * dom.value) - integral;
    }

    static class Max { // index1, index2 ?
        private final int index;
        private final int value;
        private final int width; // for "thick" extremums it can be > 1
        private boolean dominatorOfMinToTheRight;
        private boolean dominatorOfMinToTheLeft;
        Max(int idx, int value, int width) {
            this.index = idx;
            this.value = value;
            this.width = width;
        }
//        @Override
//        public String toString() {
//            return "max( " + value + " @ " + index + " w=" + width +")" + ((dominator == null) ? "" : " dominated by " + dominator);
//        }
    }
}