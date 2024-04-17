package edu.randomized_set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class RandomizedSetTest {

    @Test
    void case1() {
        RandomizedSet randomizedSet = new RandomizedSet();

        then(randomizedSet.insert(0)).isTrue();
        then(randomizedSet.insert(1)).isTrue();
        then(randomizedSet.remove(0)).isTrue();
        then(randomizedSet.insert(2)).isTrue();
        then(randomizedSet.remove(1)).isTrue();

        then(randomizedSet.getRandom()).isEqualTo(2);
    }
}