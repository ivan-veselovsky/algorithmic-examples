package edu.test8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class FindFirstAndLastTest {

    @Test
    void getFirstAndLastIndexOfValue() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        then(new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 5)).containsExactly(3, 6);
    }

    @Test
    void case2() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        then(new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 125)).containsExactly(9, 9);
    }

    @Test
    void case3() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        then(new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 2)).containsExactly(0, 0);
    }

    @Test
    void case4() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        then(new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 3)).containsExactly(1, 2);
    }

    @Test
    void case5() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        then(new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 123)).containsExactly(8, 8);
    }

    @Test
    void case6() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        thenThrownBy(()
                -> new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 1))
                .isInstanceOf(IllegalArgumentException.class);
        thenThrownBy(()
                -> new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 126))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void case7() {
        int[] a = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125};
        then(new FindFirstAndLast().getFirstAndLastIndexOfValue(a, 69))
                .containsExactly(-7, -8);
    }
}