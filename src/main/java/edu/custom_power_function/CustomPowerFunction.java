package edu.custom_power_function;

import java.util.ArrayList;
import java.util.List;

public class CustomPowerFunction {
    public double myPow(double x, int n0) {
        final long n = n0;
        if (n == 0) {
            return 1d;
        }
        if (x == 1.0d) {
            return 1d;
        }
        if (x == -1.0d) {
            return (n0 % 2 == 0) ? 1d : -1d;
        }
        long modulus_n = Math.abs(n);
        //assert modulus_n > 0;

        double product = x;
        if (modulus_n > 1) {
            List<Long> factorizedN = factorize(modulus_n);
            //assert factorizedN.size() % 2 == 0;

            long prod = product(factorizedN);
            //assert prod == modulus_n : "prod: " + prod;

            for (int i=factorizedN.size() - 2; i >=0 ; i -= 2) {
                long prime = factorizedN.get(i);
                long primePower = factorizedN.get(i + 1);
                //assert primePower >= 1;
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
        //assert n > 0;
        double p = 1.0;
        for (int j=0; j < n; j++) {
            p *= x;
        }
        return p;
    }

    static List<Long> factorize(long n) {
        List<Long> result = new ArrayList<>(32);
        long sqrt = (long)Math.sqrt(n);
        for (long i=2; (i <= sqrt) && (n > 1); ) {
            long primePower = 0;
            while (n % i == 0) {
                n /= i;
                primePower++;
            }
            if (primePower > 0) {
                result.add(i);
                result.add(primePower);
            }
            if (i >= 3) {
                i += 2;
            } else {
                i++;
            }
        }
        if (n > 1) {
            result.add(n);
            result.add(1L);
        }
        return result;
    }

    long product(List<Long> factorizedN) {
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
