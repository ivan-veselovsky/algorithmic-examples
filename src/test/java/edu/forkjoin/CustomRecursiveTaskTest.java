package edu.forkjoin;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.BDDAssertions.then;

class CustomRecursiveTaskTest {

    @Test
    void testThereIsNoPoolStarvationDeadlock() {
        int[] arr = new int[200];
        for (int i=0; i<arr.length; i++) {
            arr[i] = i;
        }

        int expectedResult = Arrays.stream(arr).sum() * 10;

        ForkJoinPool forkJoinPool = new ForkJoinPool(7);

        Integer actualResult = forkJoinPool.invoke(new CustomRecursiveTask(arr));
        then(actualResult).isEqualTo(expectedResult);
    }

}