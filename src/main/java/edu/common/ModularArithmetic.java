package edu.common;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;

public class ModularArithmetic {
    @Getter
    private final long modulo;
    private final AtomicBoolean exact = new AtomicBoolean(true); // it means, a value was %-ed by the modulo.

    public ModularArithmetic(long mod) {
        Preconditions.checkArgument(mod > 0L);
        //Preconditions.checkArgument(Primes.isPrime(mod)); // takes too long
        this.modulo = mod;
    }

    /**
     * See https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
     */
    public long moduloInverse(long a) {
        long t = 0;
        long newt = 1;
        long r = modulo;
        long newr = a;
        while (newr != 0) {
            long quotient = r / newr;

            long tmp1 = newt;
            long tmp2 = t - quotient * newt;
            t = tmp1;
            newt = tmp2;

            tmp1 = newr;
            tmp2 = r - quotient * newr;
            r = tmp1;
            newr = tmp2;
        }
        if (r > 1) {
            return -1L; // a is not invertible
        }
        if (t < 0) {
            t += modulo;
        }
        return t;
    }

    public boolean isModuloInverse(long a, long aInv) {
        return (a * aInv) % modulo == 1L;
    }

    public long prod(long a, long b) {
        a = mod(a);
        b = mod(b);
        double double_ab = (double)a * b;
        long ab = (long)double_ab;
        long c = ab / modulo;
        return mod(a * b - c * modulo);
    }

    private void setNotExact() {
        while (true) {
            boolean current = exact.get();
            if (!current || exact.compareAndSet(true, false)) {
                return;
            }
        }
    }

    public long subtract(long a, long b) {
        Preconditions.checkArgument(a >= 0 && b >= 0, " a = " + a + ", b = " + b);
        a = mod(a);
        b = mod(b);
        return mod(a - b);
    }

    /**
     * We require both positive numbers here due to
     * situation of unnecessary moduling, e.g a = 5, b = -1.
     * In such case we would module -1 to 'modulo - 1', which is unnecessary.
     * To subtract two numbers please use {@link #subtract(long, long)} operation.
     */
    public long sum(long a, long b) {
        Preconditions.checkArgument(a >= 0 && b >= 0, " a = " + a + ", b = " + b);
        a = mod(a);
        b = mod(b);
        return mod(a + b);
    }

    public long mod(final long v0) {
        long v = v0;
        if (v >= modulo || v <= -modulo) {
            setNotExact();
            v %= modulo;
        }
        if (v < 0) {
            setNotExact();
            v += modulo;
        }
        assert v >= 0 && v < modulo;
        return v;
    }

    public boolean exact() {
        return exact.get();
    }
}
