package edu.findlongestpath;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Node {
    private final int oneBasedIndex; // 1-based index as used in Heap Sort algorithm.
    private final Node left;
    private final Node right;

    @Override
    public String toString() {
        return String.valueOf(oneBasedIndex);
    }
}
