package edu.common;

public record HalfIndex(int index, boolean half) {
    public static HalfIndex ofExact(int index) {
        return new HalfIndex(index, false);
    }
    public static HalfIndex ofIndexPlusHalf(int index) {
        return new HalfIndex(index, true);
    }
    public int ceiling() {
        if (half) {
            return index + 1;
        } else {
            return index;
        }
    }
    public double asDouble() {
        return index + (half ? 0.5 : 0.);
    }
    public HalfIndex divideBy2() {
        if (index % 2 == 0) {
            return HalfIndex.ofExact(index / 2);
        } else {
            return HalfIndex.ofIndexPlusHalf(index / 2);
        }
    }
}
