package edu.primes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.primes.Primes.*;
import static org.assertj.core.api.BDDAssertions.then;

class PrimesTest {

    @Test
    void isPrime0() {
        then(Primes.isPrime(1)).isFalse();
        then(Primes.isPrime(2)).isTrue();
        then(Primes.isPrime(3)).isTrue();

        then(Primes.isPrime(5)).isTrue();
        then(Primes.isPrime(7)).isTrue();
        then(Primes.isPrime(9)).isFalse();
        then(Primes.isPrime(11)).isTrue();
        then(Primes.isPrime(13)).isTrue();
        then(Primes.isPrime(13 * 13)).isFalse();
        then(Primes.isPrime(17)).isTrue();

        then(Primes.isPrime(643)).isTrue();
        then(Primes.isPrime(43 * 43)).isFalse();
    }

    @Test
    void nextPrime1() {
        List<Long> primes = new ArrayList<>(20);
        long prime = 2;
        for (int i=0; i<50; i++) {
            prime = nextPrime(prime);
            primes.add(prime);
        }
        then(primes).hasSize(50);
        then(primes).containsExactly(3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L,
                37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L,
                79L, 83L, 89L, 97L, 101L, 103L, 107L, 109L, 113L,
                127L, 131L, 137L, 139L, 149L, 151L, 157L, 163L, 167L, 173L,
                179L, 181L, 191L, 193L, 197L, 199L, 211L, 223L, 227L, 229L, 233L);
    }

    @Test
    void factorize1() {
        then(factorize(38L)).containsExactly(2L, 1L, 19L, 1L);
        then(factorize(643L)).containsExactly(643L, 1L);
        then(factorize(123456L)).containsExactly(2L, 6L, 3L, 1L, 643L, 1L);
    }

    @Test
    void test_floorPrime0() {
        then(floorPrime(16)).isEqualTo(13);
        then(floorPrime(17)).isEqualTo(17);
        then(floorPrime(18)).isEqualTo(17);
    }

    @Test
    void test_floorPrime1() {
        then(floorPrime(Integer.MAX_VALUE / 26)).isEqualTo(82595483L);

        long p2 = floorPrime(Long.MAX_VALUE / 26);
        then(p2).isEqualTo(354745078340568241L);
        then(p2 * 26 < Long.MAX_VALUE).isTrue();

        long p3 = floorPrime(Long.MAX_VALUE / 62);
        then(p3).isEqualTo(148764065110560881L);
        then(p3 * 62 < Long.MAX_VALUE).isTrue();
    }

}