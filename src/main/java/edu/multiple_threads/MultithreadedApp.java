package edu.multiple_threads;

import lombok.SneakyThrows;

public class MultithreadedApp {
    public static void main(String[] args) {
        for (int i=1; i <= 5; i++) {
            int n = i;
            new Thread(() -> doRun(n)).start();
        }
        System.out.println("Threads started.");
        Runtime.getRuntime().addShutdownHook(
            new Thread(() -> System.out.println("Application finished.")));
    }

    @SneakyThrows
    private static void doRun(int n) {
        if (n % 4 == 0) {
            Thread.sleep(5_000);
            System.out.println("Thread " + n + " throws NPE: simulate a bug.");
            throw new NullPointerException();
        } else {
            Thread.sleep(20_000);
        }
        System.out.println("Thread " + n + " finished normally.");
    }
}
