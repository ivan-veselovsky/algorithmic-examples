package edu.reverse_bits;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class ReverseBitsTest {

    @Test
    void reverseBits() {
        int input = Integer.parseInt("00000010100101000001111010011100", 2);
        int expected = Integer.parseInt("00111001011110000010100101000000", 2);
        then(new ReverseBits().reverseBits(input)).isEqualTo(expected);
    }
}