package edu.remove_element_from_array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class RemoveElementsFromArrayTest {

    @Test
    void case1() {
        int[] nums = new int[] {3,2,2,3};
        int k = new RemoveElementsFromArray().removeElement(nums, 3);
        then(k).isEqualTo(2);
        then(Arrays.copyOfRange(nums, 0, k)).containsExactly(2,2);
    }

    @Test
    void case2() {
        int[] nums = new int[] {0,1,2,2,3,0,4,2};
        int k = new RemoveElementsFromArray().removeElement(nums, 2);
        then(k).isEqualTo(5);
        then(Arrays.copyOfRange(nums, 0, k)).containsExactly(0,1, 3,0,4);
    }
}