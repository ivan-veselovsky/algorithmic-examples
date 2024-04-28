package edu.primes;

import java.util.ArrayList;
import java.util.List;

public class Primes {
    public static boolean isPrime(long x) {
        if (x <= 1) {
            return false;
        }
        if (x <= 3) {
            return true;
        }
        if (x % 2 == 0 || x % 3 == 0) {
            return false;
        }
        long i = 5;
        while (i * i <= x) {
            if (x % i == 0 || x % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        return true;
    }

    public static long nextPrime(final long prime) {
        assert (prime == 2) || prime % 2 == 1; // 2 is the only even prime
        long candidate = (prime == 2) ? prime + 1 : prime + 2; // .., so we check only odd numbers
        while (!isPrime(candidate)) {
            if (candidate % 6 == 1) { // primes always lie on grid  (5 + k * 6) or (7 + k * 6)
                candidate += 4;
            } else {
                candidate += 2;
            }
        }
        return candidate;
    }

    public static List<Long> factorize(long n) {
        List<Long> result = new ArrayList<>(32);
        long sqrt = (long)Math.sqrt(n);
        long primeDivider = 2;
        while ((primeDivider <= sqrt) && (n > 1)) {
            long primePower = 0;
            while (n % primeDivider == 0) {
                n /= primeDivider;  // i == prime
                primePower++;
            }
            if (primePower > 0) {
                result.add(primeDivider);
                result.add(primePower);
            }
            primeDivider = nextPrime(primeDivider);
        }
        if (n > 1) {
            result.add(n);
            result.add(1L);
        }
        return result;
    }

    public static long productOfFactorizationList(List<Long> factorizedN) {
        long p = 1;
        for (int i=0; i < factorizedN.size(); i += 2) {
            long prime = factorizedN.get(i);
            long power = factorizedN.get(i + 1);
            for (int k=0; k<power; k++) {
                p *= prime;
            }
        }
        return p;
    }
}
