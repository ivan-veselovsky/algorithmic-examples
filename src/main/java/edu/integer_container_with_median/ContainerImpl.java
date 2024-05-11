package edu.integer_container_with_median;

import java.util.NavigableMap;
import java.util.TreeMap;

public class ContainerImpl implements Container {
    final BasicContainer left = new BasicContainer();
    final BasicContainer right = new BasicContainer();

    public ContainerImpl() {
    }

    @Override
    public void add(int value) {
        if (left.size() == 0) {
            left.add(value);
        } else {
            int median = left.getMax();
            if (value == median) {
                BasicContainer target = (left.size() > right.size()) ? right : left;
                target.add(value);
            } else if (value < median) {
                left.add(value);
                rebalanceLeftToRight();
            } else {
                right.add(value);
                rebalanceRightToLeft();
            }
        }
        //System.out.println("left:  " + left);
        //System.out.println("right: " + right);
    }

    private void rebalanceLeftToRight() {
        int sizeDiff = left.size() - right.size();
        if (sizeDiff > 1) {
            int leftMax = left.getMax();
            left.remove(leftMax);
            right.add(leftMax);
        }
        sizeDiff = left.size() - right.size();
        assert sizeDiff <= 1;
    }
    private void rebalanceRightToLeft() {
        int sizeDiff = left.size() - right.size();
        if (sizeDiff < 0) {
            int rightMin = right.getMin();
            right.remove(rightMin);
            left.add(rightMin);
        }
        sizeDiff = left.size() - right.size();
        assert sizeDiff <= 1;
    }

    @Override
    public boolean delete(int value) {
        if (left.remove(value)) {
            rebalanceRightToLeft();
            return true;
        }
        if (right.remove(value)) {
            rebalanceLeftToRight();
            return true;
        }
        return false;
    }

    @Override
    public int getMedian() {
        if (left.size() == 0) {
            assert right.size() == 0;
            throw new IllegalStateException("Container is empty.");
        }
        return left.getMax();
    }

    static class BasicContainer {
        private final NavigableMap<Integer, Integer> map = new TreeMap<>();
        private int size;
        void add(int i) {
            map.compute(i, (k, v) -> {
                if (v == null) {
                    return 1;
                } else {
                    return v + 1;
                }
            });
            size++;
        }
        boolean remove(int i) {
            final boolean[] result = new boolean[1];
            map.computeIfPresent(i, (k, v) -> {
                assert v > 0;
                result[0] = true;
                if (v == 1) {
                    return null;
                } else {
                    return (v - 1);
                }
            });
            if (result[0]) {
                size--;
            }
            assert size >= 0;
            return result[0];
        }
        Integer getMax() {
            return map.lastKey();
        }
        Integer getMin() {
            return map.firstKey();
        }
        int size() {
            return size;
        }
        @Override
        public String toString() {
            return String.valueOf(map);
        }
    }
}
