package edu.common;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static edu.common.MathUtils.fromLong;
import static edu.rabin_karp_substring_search.RabinKarp.q_26;
import static org.assertj.core.api.BDDAssertions.then;

class ModularArithmeticTest {

    @Test
    void modulo_inverse() {
        final ModularArithmetic mod13 = new ModularArithmetic(13);

        long a = 3;
        long actualInverse = mod13.moduloInverse(a);
        then(mod13.isModuloInverse(a, actualInverse)).isTrue();
        then(actualInverse).isEqualTo(9);

        a = 10;
        actualInverse = mod13.moduloInverse(a);
        then(mod13.isModuloInverse(a, actualInverse)).isTrue();
        then(actualInverse).isEqualTo(4);

        final ModularArithmetic mod7 = new ModularArithmetic(7);
        then(mod7.moduloInverse(10)).isEqualTo(5);
    }

    @Test
    void test_mod() {
        ModularArithmetic mod = new ModularArithmetic(7);

        then(mod.mod(0)).isEqualTo(0L);
        then(mod.mod(5)).isEqualTo(5L);
        then(mod.mod(7)).isEqualTo(0L);

        then(mod.mod(-1L)).isEqualTo(6L);
        then(mod.mod(-5L)).isEqualTo(2L);
        then(mod.mod(-7L)).isEqualTo(0L);
    }

    @Test
    void test_sum() {
        ModularArithmetic mod = new ModularArithmetic(7);

        then(mod.sum(0, 1)).isEqualTo(1L);
        then(mod.sum(5, 3)).isEqualTo(1L);

        then(mod.subtract(1, 2)).isEqualTo(6L);
        then(mod.subtract(0, 15)).isEqualTo(6L);
        then(mod.subtract(1, 1)).isEqualTo(0L);
        then(mod.subtract(3, 5)).isEqualTo(5L);
    }

    @Test
    void test_prod_1() {
        final ModularArithmetic mod_q_26 = new ModularArithmetic(q_26);

        long actual = mod_q_26.prod(13L, 643L);
        then(actual).isEqualTo(8359L);
        then(mod_q_26.exact()).isTrue();

        actual = mod_q_26.prod(q_26, q_26);
        then(actual).isEqualTo(0L);
        then(mod_q_26.exact()).isFalse();

        actual = mod_q_26.prod(Long.MAX_VALUE, q_26);
        then(actual).isEqualTo(0L);

        then((double)777777713L * q_26).isGreaterThan(Long.MAX_VALUE);
        ModularArithmetic mod643 = new ModularArithmetic(643L);
        actual = mod643.prod(777777713L, q_26);
        then(actual).isEqualTo(514L);

        BigInteger q_26_bi = fromLong(q_26);
        BigInteger xBi = fromLong(q_26 - 7);
        BigInteger yBi = fromLong(q_26 + 13);
        BigInteger fullProdBi = xBi.multiply(yBi);
        BigInteger maxLongBi = fromLong(Long.MAX_VALUE);
        then(fullProdBi.compareTo(maxLongBi)).isGreaterThan(0);
        long expected = fullProdBi.remainder(q_26_bi).longValueExact();
        then(mod_q_26.prod(xBi.longValueExact(), yBi.longValueExact())).isEqualTo(expected);
    }

    @Test
    void test_prod_2() {
        ModularArithmetic mod7 = new ModularArithmetic(7);

        then(mod7.prod(0, 1)).isEqualTo(0L);
        then(mod7.prod(5, 3)).isEqualTo(1L);
        then(mod7.prod(-1, 1)).isEqualTo(6L);

        then(mod7.prod(1, -2)).isEqualTo(5L);
        then(mod7.prod(-5, -3)).isEqualTo(1L);
        then(mod7.prod(-8, 5)).isEqualTo(2L);
    }

    @Test
    void test_inv() {
        ModularArithmetic mod = new ModularArithmetic(7);
        long inv_10 = mod.moduloInverse(10);
        then(inv_10).isEqualTo(5);
        then(mod.isModuloInverse(10, 5)).isTrue();
    }
}