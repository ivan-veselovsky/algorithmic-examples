package edu.gas_stations_circuit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class GasStationTest {

    @Test
    void canCompleteCircuit() {
        int[] gas = new int[] {1,2,3,4,5};
        int[] cost = new int[] {3,4,5,1,2};

        then(new GasStation().canCompleteCircuit(gas, cost)).isEqualTo(3);
    }

    @Test
    void canCompleteCircuit1() {
        int[] gas = new int[] {2,3,4};
        int[] cost = new int[] {3,4,3};

        then(new GasStation().canCompleteCircuit(gas, cost)).isEqualTo(-1);
    }

    @Test
    void canCompleteCircuit2() {
        int[] gas =  new int[] {2, 0, 0, 0, 0};
        int[] cost = new int[] {0, 1, 0, 0, 0};

        then(new GasStation().canCompleteCircuit(gas, cost)).isEqualTo(0);
    }

}