package edu.common;

import com.google.common.base.Preconditions;
import lombok.Getter;

@Getter
public class ModularArithmetic {
    private final long modulo;
    private long modCount;

    private final ModuloArithmeticStrict debug_mod_strict;

    public ModularArithmetic(final long mod) {
        Preconditions.checkArgument(mod > 0L);
        //Preconditions.checkArgument(Primes.isPrime(mod)); // takes too long
        this.modulo = mod;
        debug_mod_strict = new ModuloArithmeticStrict(mod);
    }

    /**
     * See https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
     */
    public long moduloInverse(final long a) {
        long v = invImpl(a);

        long v1 = debug_mod_strict.moduloInverse(a);
        assert v1 == v;
        assert modCount() == debug_mod_strict.modCount() : modCount() + " == " + debug_mod_strict.modCount();

        return v;
    }

    private long invImpl(long a) {
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

    public long prod(final long a0, final long b0) {
//        long a = mod(a0);
//        long b = mod(b0);
//        double double_ab = (double)a * b;
//        long ab = (long)double_ab;
//        long c = ab / modulo;
//        // TODO: in some cases (e.g 3 * 5 mod 7) modCount is lost here:
//        long prod0 = mod(a * b - c * modulo);

        long modCount0 = debug_mod_strict.modCount();
        long prod1 = debug_mod_strict.prod(a0, b0);
        long modCount1 = debug_mod_strict.modCount();
        this.modCount += (modCount1 - modCount0);
        //assert prod1 == prod0;
        //assert modCount() == debug_mod_strict.modCount() : modCount() + " == " + debug_mod_strict.modCount();

        return prod1;
    }

    protected final void modIncrease() {
        assert modCount < Long.MAX_VALUE;
        modCount++;
    }

    public long subtract(final long a0, final long b0) {
        Preconditions.checkArgument(a0 >= 0 && b0 >= 0, " a = " + a0 + ", b = " + b0);
        long a = mod(a0);
        long b = mod(b0);
        long result0 = mod(a - b);

        long result1 = debug_mod_strict.subtract(a0, b0);
        assert result0 == result1;
        assert modCount() == debug_mod_strict.modCount() : modCount() + " == " + debug_mod_strict.modCount();
        return result0;
    }

    /**
     * We require both positive numbers here due to
     * situation of unnecessary moduling, e.g a = 5, b = -1.
     * In such case we would module -1 to 'modulo - 1', which is unnecessary.
     * To subtract two numbers please use {@link #subtract(long, long)} operation.
     */
    public long sum(final long a0, final long b0) {
        Preconditions.checkArgument(a0 >= 0 && b0 >= 0, " a = " + a0 + ", b = " + b0);
        long a = mod(a0);
        long b = mod(b0);
        long result0 = mod(a + b);

        long result1 = debug_mod_strict.sum(a0, b0);
        assert result0 == result1;
        assert modCount() == debug_mod_strict.modCount() : modCount() + " == " + debug_mod_strict.modCount();
        return result0;
    }

    public long mod(final long v0) {
        long v = v0;
        boolean mod = false;
        if (v >= modulo || v <= -modulo) {
            v %= modulo;
            mod = true;
        }
        if (v < 0) {
            v += modulo;
            mod = true;
        }
        if (mod) {
            modIncrease();
        }
        assert v >= 0 && v < modulo;

//        long result1 = debug_mod_strict.mod(v0);
//        assert v == result1;
//        assert modCount() == debug_mod_strict.modCount() : modCount() + " == " + debug_mod_strict.modCount();

        return v;
    }
}
