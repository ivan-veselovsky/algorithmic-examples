package edu.common;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.function.IntFunction;

import static edu.common.MathUtils.fromLong;
import static edu.common.MathUtils.sign;
import static edu.rabin_karp_substring_search.RabinKarp.q_26;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void test_fromLong() {
        BigInteger b1 = new BigInteger(new byte[] {1, 127});
        then(b1.longValue()).isEqualTo(127 + 256);

        long x = q_26;
        then(fromLong(x).longValue()).isEqualTo(x);

        x = -1L;
        then(fromLong(x).longValue()).isEqualTo(x);

        x = 1L;
        then(fromLong(x).longValue()).isEqualTo(x);

        x = Long.MAX_VALUE;
        then(fromLong(x).longValue()).isEqualTo(x);

        x = Long.MIN_VALUE;
        then(fromLong(x).longValue()).isEqualTo(x);
    }

    @Test
    void bisectFindRoot() {
        IntFunction<Integer> f = x -> (x >=5) ? -1 : 1;
        HalfIndex actual = MathUtils.bisectFindRoot(f, -10, 10);
        then(actual.asDouble()).isEqualTo(4.5);

        actual = MathUtils.bisectFindRoot(MathUtils::sign, -10, 10);
        then(actual.asDouble()).isEqualTo(0.);
    }
}