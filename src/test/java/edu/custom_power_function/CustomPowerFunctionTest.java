package edu.custom_power_function;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class CustomPowerFunctionTest {

    @Test
    void case1() {
        double actual = new CustomPowerFunction().myPow(2.0, 10);
        then(actual).isEqualTo(1024.);
    }

    @Test
    void case2() {
        double actual = new CustomPowerFunction().myPow(2.1, 3);
        then(actual).isEqualTo(9.261, Offset.offset(1e-15));
    }

    @Test
    void case3() {
        double actual = new CustomPowerFunction().myPow(2.0, -2);
        then(actual).isEqualTo(0.25);
    }

}