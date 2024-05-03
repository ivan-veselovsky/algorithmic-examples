package edu.add_binary_67;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class AddBinaryTest {

    @Test
    void case1() {
        then(new AddBinary().addBinary("11", "1")).isEqualTo("100");
    }
    @Test
    void case2() {
        then(new AddBinary().addBinary("1010", "1011")).isEqualTo("10101");
    }
    @Test
    void case3() {
        then(new AddBinary().addBinary("10000", "11")).isEqualTo("10011");
    }
}