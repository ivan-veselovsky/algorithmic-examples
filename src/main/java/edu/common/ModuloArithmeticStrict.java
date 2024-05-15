package edu.common;

import lombok.Getter;

import java.math.BigInteger;

import static edu.common.MathUtils.fromLong;

public class ModuloArithmeticStrict {

    @Getter
    private final long modulus;
    private final BigInteger modulusBi;
    @Getter
    private long modCount = 0;

    ModuloArithmeticStrict(long modulus) {
        this.modulus = modulus;
        this.modulusBi = fromLong(modulus);
    }

    private BigInteger modBi(BigInteger bi) {
        BigInteger moduled = bi.mod(modulusBi); // >= 0
        if (bi.compareTo(moduled) != 0) {
            modCount++;
            return moduled;
        }
        return bi;
    }

    private long mod(BigInteger bi) {
        return modBi(bi).longValueExact();
    }

    public long sum(final long a, final long b) {
        assert a >= 0 && b >= 0;
        BigInteger aBi = modBi(fromLong(a));
        BigInteger bBi = modBi(fromLong(b));
        return mod(aBi.add(bBi));
    }

    public long subtract(final long a, final long b) {
        assert a >= 0 && b >= 0;
        BigInteger aBi = modBi(fromLong(a));
        BigInteger bBi = modBi(fromLong(b));
        return mod(aBi.subtract(bBi));
    }

    public long prod(final long a, final long b) {
        //assert a >= 0 && b >= 0;
        BigInteger aBi = modBi(fromLong(a));
        BigInteger bBi = modBi(fromLong(b));
        return mod(aBi.multiply(bBi));
    }

    public long moduloInverse(long a) {
        assert a >= 0;
        BigInteger aBi = fromLong(a);
        return mod(aBi.modInverse(modulusBi));
    }
}
