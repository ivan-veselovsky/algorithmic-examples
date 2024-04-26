package edu.happy_number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class HappyNumberTest {

    @Test
    void case1() {
        boolean happy = new HappyNumber().isHappy(2);
        then(happy).isFalse();
    }

    @Test
    void case2() {
        boolean happy = new HappyNumber().isHappy(19);
        then(happy).isTrue();
    }
}