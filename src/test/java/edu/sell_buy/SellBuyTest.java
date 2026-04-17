package edu.sell_buy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class SellBuyTest {

    @Test
    void case1() {
        int[] prices = { 10, 2, 1, 4, 3, 0, 1, -1, 2, -7, -1};
        int[] buySell = new SellBuy().sellBuy(prices);
        then(buySell).containsExactly(9, 10);
    }

    @Test
    void case2() {
        int[] prices = { 1, 10, 2, 1, 4, 3, 11 };
        int[] buySell = new SellBuy().sellBuy(prices);
        then(buySell).containsExactly(0, 6);
    }

    @Test
    void case3() {
        int[] prices = { 3, 5, 1, 4 };
        int[] buySell = new SellBuy().sellBuy(prices);
        then(buySell).containsExactly(2, 3);
    }
}