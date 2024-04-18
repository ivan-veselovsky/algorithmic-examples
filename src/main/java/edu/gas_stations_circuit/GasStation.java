package edu.gas_stations_circuit;

public class GasStation {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        assert gas.length == cost.length;
        int sumBalance = 0;
        int minBalance = 0;
        int minBalanceIndex = -1;
        for (int i=0; i<gas.length; i++) {
            int delta = (gas[i] - cost[i]);
            sumBalance += delta;
            if (i == 0 || sumBalance < minBalance) {
                minBalance = sumBalance;
                minBalanceIndex = i;
            }
        }
        if (sumBalance < 0) {
            return -1;
        }
        if (gas.length == 1) {
            return 0;
        }
        // find first positive after minimum of balance:
        for (int i=0; i<gas.length; i++) {
            int index = (i + minBalanceIndex) % gas.length;
            int delta = (gas[index] - cost[index]);
            if (delta > 0) {
                return index;
            }
        }
        assert false;
        return -1;
    }

}
