package edu.remove_duplicates_from_sorted_array;

import edu.remove_element_from_array.RemoveElementsFromArray;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class RemoveDuplicatesFromSortedArrayTest {

    @Test
    void case1() {
        int[] nums = new int[] {1,1,2};
        int k = new RemoveDuplicatesFromSortedArray().removeDuplicates(nums);
        then(k).isEqualTo(2);
        then(Arrays.copyOfRange(nums, 0, k)).containsExactly(1,2);
    }

    @Test
    void case2() {
        int[] nums = new int[] {0,0,1,1,1,2,2,3,3,4};
        int k = new RemoveDuplicatesFromSortedArray().removeDuplicates(nums);
        then(k).isEqualTo(5);
        then(Arrays.copyOfRange(nums, 0, k)).containsExactly(0,1, 2, 3,4);
    }
}