package edu.common;

import org.junit.jupiter.api.Test;

import static edu.rabin_karp_substring_search.RabinKarp.q_26;
import static org.assertj.core.api.BDDAssertions.then;

class ModularArithmeticTest {

    @Test
    void modulo_inverse() {
        final ModularArithmetic mod = new ModularArithmetic(13L);

        long a = 3;
        long actualInverse = mod.moduloInverse(a);
        then(mod.isModuloInverse(a, actualInverse)).isTrue();
        then(actualInverse).isEqualTo(9);

        a = 10;
        actualInverse = mod.moduloInverse(a);
        then(mod.isModuloInverse(a, actualInverse)).isTrue();
        then(actualInverse).isEqualTo(4);
    }

    @Test
    void prod_mod_0() {
        ModularArithmetic mod = new ModularArithmetic(q_26);

        long actual = mod.prod(13L, 643L);
        then(actual).isEqualTo(8359L);
        then(mod.exact()).isTrue();

        actual = mod.prod(q_26, q_26);
        then(actual).isEqualTo(0L);
        then(mod.exact()).isFalse();

        actual = mod.prod(Long.MAX_VALUE, q_26);
        then(actual).isEqualTo(0L);

        then((double)777777713L * q_26).isGreaterThan(Long.MAX_VALUE);
        mod = new ModularArithmetic(643L);
        actual = mod.prod(777777713L, q_26);
        then(actual).isEqualTo(514L);
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
        then(mod.sum(-1, 1)).isEqualTo(0L);

        then(mod.sum(1, -2)).isEqualTo(6L);
        then(mod.sum(-5, -3)).isEqualTo(6L);
        then(mod.sum(-15, 0)).isEqualTo(6L);
    }

    @Test
    void test_prod() {
        ModularArithmetic mod = new ModularArithmetic(7);

        then(mod.prod(0, 1)).isEqualTo(0L);
        then(mod.prod(5, 3)).isEqualTo(1L);
        then(mod.prod(-1, 1)).isEqualTo(6L);

        then(mod.prod(1, -2)).isEqualTo(5L);
        then(mod.prod(-5, -3)).isEqualTo(1L);
        then(mod.prod(-8, 5)).isEqualTo(2L);
    }

    @Test
    void test_inv() {
        ModularArithmetic mod = new ModularArithmetic(7);
        long inv_10 = mod.moduloInverse(10);
        then(inv_10).isEqualTo(5);
        then(mod.isModuloInverse(10, 5)).isTrue();
    }
}