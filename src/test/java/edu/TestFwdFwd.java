package edu;

import lombok.val;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class TestFwdFwd {

    @Test
    void test_fwd_fwd() {
        var r_long=0.0301;
        val t_long=153.;

        var r_short=0.0236;
        val t_short=61.;

        val t_ff = 92.;
        val T=360.;

        val ff_bid = ((1 + r_long * t_long / T)/(1 + r_short * t_short / T) - 1) * T / t_ff;
        System.out.println(ff_bid);

        r_long = 0.0305;
        r_short = 0.0233;

        val ff_ask = ((1 + r_long * t_long / T)/(1 + r_short * t_short / T) - 1) * T / t_ff;
        System.out.println(ff_ask);

        then(ff_bid).isEqualTo(0.03427272977, Offset.offset(1e-7));
        then(ff_ask).isEqualTo(0.03513519733, Offset.offset(1e-7));
    }
}
