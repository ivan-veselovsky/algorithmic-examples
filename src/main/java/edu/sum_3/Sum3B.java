package edu.sum_3;

import java.util.*;
import java.util.function.IntUnaryOperator;

public class Sum3B {
    private int[] weights;
    private final List<int[]> threeIndexList = new ArrayList<>();

    public List<List<Integer>> threeSum(int[] nums0) {
        Arrays.sort(nums0);

        weights = nums0;
        System.out.println(Arrays.toString(weights));

        if (nums0.length <= 2) {
            return List.of();
        }

        Range range = Range.full(weights);
        while (range != null) {
            Range range2 = computeAllTriplesOfRange(range);
            //System.out.println("Range: " + range + " -> " + range2);
            range = range2;
        }

        return composeAnswer();
    }

    /**
     * Gets all triples with fixed right edge of the range.
     * @param range0
     * @return smaller range
     */
    Range computeAllTriplesOfRange(final Range range0) {
        Range xToZRange = range0;
        if (range0.length() < 3) {
            return null;
        }
        Range searchY = xToZRange.shrinkLeftAndRight();
        if (searchY.isEmpty()) {
            return null;
        }
        int loopCount = 0;
        while (true) {
            loopCount++;
            int key_y = key(xToZRange);
            HalfIndexAndCount index_of_key_y = stableBinarySearch(weights, searchY, key_y);
            if (index_of_key_y.isLessThan(searchY.first())) {
                break;
            } else if (index_of_key_y.isGreaterThan(searchY.last())) {
                if (loopCount == 1) {
                    return range0.shrinkLeftAll(q -> weights[q]);
                } else {
                    break;
                }
            } else {
                // we're in the "between" range:
                assert searchY.contains(index_of_key_y.leftmostKeyIndex());
                final int newY;
                if (index_of_key_y.isEven()) { // value found, collect it:
                    int x = xToZRange.first();
                    int y = index_of_key_y.leftmostKeyIndex(); // always leftmost? really?
                    int z = xToZRange.last();
                    collect3Index(x, y, z);
                    newY = y - 1;
                } else {
                    newY = index_of_key_y.leftmostKeyIndex(); // to the left from half-index
                }

                xToZRange = xToZRange.shrinkLeft(); // x++

                int newX = xToZRange.first() + 1;
                if (Range.lengthInclusive(newX, newY) < 1) {
                    break;
                } else {
                    searchY = Range.ofInclusive(newX, newY);
                }
                assert !searchY.isEmpty();
            }
        }

        // common exit point -- shift z to the left:
        return range0.shrinkRightAll(q -> weights[q]);
    }

    int key(Range r) {
        assert r.length() > 1;
        return -(weights[r.first()] + weights[r.last()]);
    }

    record Range(int first, int lastExclusive) {
        Range {
            assert first <= lastExclusive : " " + first + " -> " + lastExclusive;
        }
        static Range full(int[] array) {
            return new Range(0, array.length);
        }
        boolean isEmpty() {
            return first == lastExclusive;
        }
        int length() {
            return lastExclusive - first;
        }
        /** inclusive */
        int last() {
            return lastExclusive - 1;
        }
        static Range of(int x, int yExcl) {
            return new Range(x, yExcl);
        }
        static int lengthInclusive(int x, int y) {
            return (y - x + 1);
        }
        static Range ofInclusive(int x, int y) {
            assert y >= x - 1 : "Incorrect range: " + x + ", " + y;
            return new Range(x, y + 1);
        }
        Range shrinkLeft() {
            Range r = new Range(first + 1, lastExclusive);
            assert r.length() + 1 == length();
            return r;
        }
        Range shrinkLeftAll(IntUnaryOperator f) {
            if (isEmpty()) {
                return this;
            }
            final int firstV = f.applyAsInt(first);
            int removeCount = 1;
            for (int i=first + 1; i < lastExclusive; i++) {
                if (f.applyAsInt(i) == firstV) {
                    removeCount++;
                }
            }
            Range r = Range.of(first + removeCount, lastExclusive);
            assert r.length() + removeCount == length();
            return r;
        }
        Range shrinkRight() {
            Range r = new Range(first, lastExclusive - 1);
            assert r.length() + 1 == length();
            return r;
        }
        Range shrinkRightAll(IntUnaryOperator f) {
            if (isEmpty()) {
                return this;
            }
            final int lastV = f.applyAsInt(lastExclusive - 1);
            int removeCount = 1;
            for (int i=lastExclusive - 2; i >= first; i--) {
                if (f.applyAsInt(i) == lastV) {
                    removeCount++;
                }
            }
            Range r = Range.of(first, lastExclusive - removeCount);
            assert r.length() + removeCount == length();
            return r;
        }
        Range shrinkLeftAndRight() {
            Range r = new Range(first + 1, lastExclusive - 1);
            assert r.length() + 2 == length();
            return r;
        }
        boolean contains(int idx) {
            return (idx >= first) && (idx < lastExclusive);
        }
        @Override
        public String toString() {
            return "[" + first + "," + (lastExclusive - 1) + ']';
        }
    }

    public record HalfIndexAndCount(int leftmostKeyIndex, int half, int keyCount) {
        public HalfIndexAndCount {
            assert (half == 0) ^ (keyCount == 0);
        }
        static HalfIndexAndCount ofPlusHalf(int idx) {
            return new HalfIndexAndCount(idx, 1,0);
        }
        static HalfIndexAndCount ofMinusHalf(int idx) {
            return new HalfIndexAndCount(idx,  -1, 0);
        }
        static HalfIndexAndCount ofEven(int idx, int count) {
            assert count > 0;
            return new HalfIndexAndCount(idx, 0, count);
        }
        boolean isLessThan(int idx) {
            return (leftmostKeyIndex + half) < idx;
        }
        boolean isGreaterThan(int idx) {
            return (leftmostKeyIndex + half) > idx;
        }
        boolean isEven() {
            return (half == 0);
        }
        public int leftmostKeyIndex() {
            if (half == 0) {
                return leftmostKeyIndex;
            } else if (half > 0) {
                return leftmostKeyIndex;
            } else {
                return leftmostKeyIndex - 1;
            }
        }
        @Override
        public String toString() {
            if (half == 0) {
                return leftmostKeyIndex + (keyCount() > 0 ? (":" + keyCount) : "");
            } else {
                double d = leftmostKeyIndex + half / 2.0;
                return d + (keyCount() > 0 ? (":" + keyCount) : "");
            }
        }
    }

    public static HalfIndexAndCount stableBinarySearch(int[] arr, final Range range, final int key) {
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Empty range: " + range);
        }
        if (arr[range.last()] < key) {
            return HalfIndexAndCount.ofPlusHalf(range.last());
        }
        if (arr[range.first()] > key) {
            return HalfIndexAndCount.ofMinusHalf(range.first());
        }
        int binSearchResult = Arrays.binarySearch(arr, range.first(), range.lastExclusive(), key);
        if (binSearchResult >= 0) {
            int leftKeyCount = 0;
            int i = binSearchResult;
            while (true) {
                i--;
                if (i < range.first() || arr[i] != key) {
                    break;
                }
                leftKeyCount++;
            }
            i = binSearchResult;
            int rightKeyCount = 0;
            while (true) {
                i++;
                if (i == range.lastExclusive() || arr[i] != key) {
                    break;
                }
                rightKeyCount++;
            }
            int keyCount = leftKeyCount + 1 + rightKeyCount;
            int index = binSearchResult - leftKeyCount;
            assert index >= range.first();
            assert index <= range.last();
            return HalfIndexAndCount.ofEven(index, keyCount);
        } else {
            int insertionPosition = - binSearchResult - 1;
            return HalfIndexAndCount.ofMinusHalf(insertionPosition);
        }
    }

    void collect3Index(int x, int y, int z) {
        assert x != y : x + " != " + y;
        assert y != z : y + " != " + z;
        assert z != x : z + " != " + x;
        assert weights[x] + weights[y] + weights[z] == 0;
        int[] arr = new int[] {x, y, z};
//        System.out.println("collected: idx=" + Arrays.toString(arr)
//                + " = (" + weights[x] + ", " +  weights[y] + ", " + weights[z] + ")");
        threeIndexList.add(arr);
    }

    private List<List<Integer>> composeAnswer() {
        TreeSet<Values> treeSet = new TreeSet<>(cmp);
        for (int[] indices: threeIndexList) {
            Values v = new Values(weights[indices[0]],
                    weights[indices[1]], weights[indices[2]]);
            treeSet.add(v);
        }
        return treeSet.stream().map(Values::toList).toList();
    }

    final Comparator<Values> cmp = (v1, v2) -> {
        if (v1 == v2) {
            return 0;
        }
        int diff = v1.x() - v2.x();
        if (diff != 0) {
            return diff;
        }
        diff = v1.y() - v2.y();
        return diff;
    };

    record Values(int x, int y, int z) {
        Values {
            assert x + y + z == 0;
        }
        List<Integer> toList() {
            return List.of(x, y, z);
        }
    }
}
