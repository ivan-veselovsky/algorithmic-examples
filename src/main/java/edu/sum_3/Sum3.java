//package edu.sum_3;
//
//import java.util.*;
//
//public class Sum3 {
//    private int[] weights;
//    private final List<int[]> threeIndexList = new ArrayList<>();
//
//    public List<List<Integer>> threeSum(int[] nums0) {
//        Arrays.sort(nums0);
//
//        weights = nums0;
//
//        if (nums0.length <= 2) {
//            return List.of();
//        }
//
//        final IndexAndCount zeroIndex = stableBinarySearch(weights, 0, weights.length, 0);
//        final int middle = zeroIndex.realOrAssumedIndex();
//
//        System.out.println("part 1: N-N-P");
//
//        int x = 0;
//        int z = weights.length - 1;
//        int y = middle; // exclusive
//        while (x < z) {
//            int key_y = -(weights[x] + weights[z]);
//            IndexAndCount index_of_key_y = stableBinarySearch(weights, x + 1, y, key_y);
//            if (index_of_key_y.outLeft()) {
//                z--;
//                y = middle;
//            } else {
//                if (index_of_key_y.keyCount() > 0) { // value found, collect it
//                    collect3Index(x, index_of_key_y.realOrAssumedIndex(), z);
//                }
//                y = index_of_key_y.realOrAssumedIndex() + 1;
//                x++;
//            }
//        }
//
//        System.out.println("part 2: N-P-P");
//
//        x = 0;
//        y = middle; // inclusive
//        z = weights.length - 1;
//        while (x < z) {
//            int key_y = -(weights[x] + weights[z]);
//            IndexAndCount index_of_key_y = stableBinarySearch(weights, y, z, key_y);
//            if (index_of_key_y.outRight()) {
//                x++;
//                y = middle;
//            } else {
//                y = index_of_key_y.realOrAssumedIndex(); // TODO: leftmost
//                if (index_of_key_y.keyCount() > 0) {
//                    collect3Index(x, y, z);
//                }
//                z--;
//            }
//        }
////        while (x < z) {
////            int key_y = -(weights[x] + weights[z]);
////            IndexAndCount index_of_key_y = stableBinarySearch(weights, y, z, key_y);
////            if (index_of_key_y.keyCount() == 0) {
////                assert index_of_key_y.leftmostKeyIndex() < 0;
////                if (index_of_key_y.outRight()) {
////                    x++;
////                    y = middle;
////                } else {
////                    z--;
////                }
////            } else {
////                y = index_of_key_y.leftmostKeyIndex(); // rightmost not to miss situation like -2, -2 .... , 4
////                collect3Index(x, y, z);
////                z--;
////            }
////        }
//
//        return composeAnswer();
//    }
//
//    public record IndexAndCount(int leftmostKeyIndex, int keyCount, boolean outLeft, boolean outRight) {
//        int realOrAssumedIndex() {
//            if (leftmostKeyIndex >= 0) {
//                return leftmostKeyIndex;
//            } else {
//                return -leftmostKeyIndex - 1;
//            }
//        }
//        int rightmostIndex() {
//            return leftmostKeyIndex + keyCount - 1;
//        }
//    }
//
//    public static IndexAndCount stableBinarySearch(int[] arr, final int fromIncl, final int toExcl, final int key) {
//        if (fromIncl == toExcl) {
//            throw new IllegalArgumentException("From " + fromIncl + " , to excl : " + toExcl);
//            //return new IndexAndCount(-fromIncl - 1, 0, false, false);
//        }
//        if (arr[toExcl - 1] < key) {
//            return new IndexAndCount(-toExcl - 1, 0, false, true);
//        }
//        if (arr[fromIncl] > key) {
//            return new IndexAndCount(-fromIncl - 1, 0, true, false);
//        }
//        int binSearchResult = Arrays.binarySearch(arr, fromIncl, toExcl, key);
//        if (binSearchResult >= 0) {
//            int leftKeyCount = 0;
//            int i = binSearchResult;
//            while (true) {
//                i--;
//                if (i < fromIncl || arr[i] != key) {
//                    break;
//                }
//                leftKeyCount++;
//            }
//            i = binSearchResult;
//            int rightKeyCount = 0;
//            while (true) {
//                i++;
//                if (i == toExcl || arr[i] != key) {
//                    break;
//                }
//                rightKeyCount++;
//            }
//            int keyCount = leftKeyCount + 1 + rightKeyCount;
//            return new IndexAndCount(binSearchResult - leftKeyCount, keyCount, false, false);
//        } else {
//            return new IndexAndCount(binSearchResult, 0, false, false);
//        }
//    }
//
//    void collect3Index(int x, int y, int z) {
//        assert x != y : x + " != " + y;
//        assert y != z : y + " != " + z;
//        assert z != x : z + " != " + x;
//        assert weights[x] + weights[y] + weights[z] == 0;
//        int[] arr = new int[] {x, y, z};
//        System.out.println("collected: idx=" + Arrays.toString(arr)
//                + " = (" + weights[x] + ", " +  weights[y] + ", " + weights[z] + ")");
//        threeIndexList.add(arr);
//    }
//
//    private List<List<Integer>> composeAnswer() {
//        TreeSet<Values> treeSet = new TreeSet<>(cmp);
//        for (int[] indices: threeIndexList) {
//            Values v = new Values(weights[indices[0]],
//                    weights[indices[1]], weights[indices[2]]);
//            treeSet.add(v);
//        }
//        return treeSet.stream().map(Values::toList).toList();
//    }
//
//    final Comparator<Values> cmp = (v1, v2) -> {
//        if (v1 == v2) {
//            return 0;
//        }
//        int diff = v1.x() - v2.x();
//        if (diff != 0) {
//            return diff;
//        }
//        diff = v1.y() - v2.y();
//        return diff;
//    };
//
//    record Values(int x, int y, int z) {
//        Values {
//            assert x + y + z == 0;
//        }
////        public boolean isDuplicate(Values values) {
////            if (values == null) {
////                return false;
////            }
////            return x == values.x() && y == values.y();
////        }
//        List<Integer> toList() {
//            return List.of(x, y, z);
//        }
//    }
//}
