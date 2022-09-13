package edu.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Example of ForkJoin task usage: it shows that pool starvation deadlock does not happen
 * because ForkJoinPool can allow other tasks to proceed while some executors seem to
 * be blocked in ForkJoinTask.join();
 */
public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private final int[] arr;

    private static final int THRESHOLD = 3;

    CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(arr);
        }
    }

    private Collection<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>(2);
        dividedTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, 0, arr.length / 2)));
        dividedTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return dividedTasks;
    }

    private int processing(int[] arr) {
        System.out.println(Thread.currentThread() + ": processing -> " + Arrays.toString(arr));
        return Arrays.stream(arr)
                .map(a -> a * 10)
                .sum();
    }
}
