package edu.majority_element_moore_voting_algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class MajorityElementTest {

    @Test
    void case1() {
        int majority = new MajorityElement().majorityElement(new int[] {3,2,3});
        then(majority).isEqualTo(3);
    }

    @Test
    void case2() {
        int majority = new MajorityElement().majorityElement(new int[] {2,2,1,1,1,2,2});
        then(majority).isEqualTo(2);
    }
}