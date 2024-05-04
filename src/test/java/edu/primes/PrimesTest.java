package edu.primes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.primes.Primes.factorize;
import static edu.primes.Primes.nextPrime;
import static org.assertj.core.api.BDDAssertions.then;

class PrimesTest {

    @Test
    void isPrime() {
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
}