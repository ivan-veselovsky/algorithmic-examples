package edu.custom_power_function;

import java.util.List;

import static edu.primes.Primes.factorize;
import static edu.primes.Primes.productOfFactorizationList;

public class CustomPowerFunction {
    public double myPow(double x, final int n0) {
        final long n = n0;
        if (n == 0) {
            return 1d;
        }
        if (x == 1.0d) {
            return 1d;
        }
        if (x == -1.0d) {
            return (n % 2 == 0) ? 1d : -1d;
        }
        long modulus_n = Math.abs(n);
        assert modulus_n > 0;

        double product = x;
        if (modulus_n > 1) {
            List<Long> factorizedN = factorize(modulus_n);
            assert factorizedN.size() % 2 == 0;

            long prod = productOfFactorizationList(factorizedN);
            assert prod == modulus_n : "prod: " + prod;

            for (int i=0; i < factorizedN.size(); i += 2) {
                long prime = factorizedN.get(i);
                long primePower = factorizedN.get(i + 1);
                assert primePower >= 1;
                for (int k=0; k < primePower; k++) {
                    product = simplePower(product, prime);
                }
            }
        }
        if (n < 0) {
            return 1d / product;
        }
        return product;
    }

    /** n times multiplies x to itself. */
    static double simplePower(double x, long n) {
        assert n > 0;
        double p = 1.0;
        for (int j=0; j < n; j++) {
            p *= x;
        }
        return p;
    }
}
