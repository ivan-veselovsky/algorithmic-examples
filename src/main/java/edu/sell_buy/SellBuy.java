package edu.sell_buy;

public class SellBuy {
    public int[] sellBuy(int[] prices) {
        IndexAndValue min = new IndexAndValue(0, prices[0]); // global minimum
        IndexAndValue x = new IndexAndValue(0, prices[0]);
        IndexAndValue y = new IndexAndValue(0, prices[0]);

        for (int i=0; i < prices.length; i++) {
            int p = prices[i];

            if (p > y.value()) {
                y = new IndexAndValue(i, p); // extend max
            } else if (p < min.value()) {
                min = new IndexAndValue(i, p); // renew min
            } else if ((p - min.value()) > (y.value() - x.value())) {
                x = min;
                y = new IndexAndValue(i, p);
            }
        }

        return new int[] { x.index(), y.index() };
    }

    record IndexAndValue(int index, int value) {
    }
}
