package edu.selector_api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;

class EchoClientTest {

    @Test
    void basic_request_response() {
        try (final EchoServer echoServer = new EchoServer()) {
            CompletableFuture.runAsync(echoServer::run);

            try (EchoClient echoClient = new EchoClient()) {
                String response = echoClient.sendMessage("Foo Bar!");
                System.out.println("Response: [" + response + "]");
                then(response).isEqualTo("!raB ooF");
            }
        }
    }

    @Test
    void multithreaded_load() {
        try (final EchoServer echoServer = new EchoServer()) {
            CompletableFuture.runAsync(echoServer::run);

            try (ExecutorService service = Executors.newFixedThreadPool(20)) {
                for (int t=0; t<20; t++) {
                    int threadId = t;
                    service.submit(() -> doJob(threadId));
                }
            }
        }
    }

    @SneakyThrows
    private static void doJob(int threadId) {
        try (EchoClient echoClient = new EchoClient()) {
            for (int k=0; k<1000; k++) {
                String message = "Thread " + threadId + " , message #" + k;
                String response = echoClient.sendMessage(message);
                then(response).isEqualTo(revert(message));
            }
        }
    }

    static String revert(String x) {
        if (x.length() < 2) {
            return x;
        }
        char[] cc = x.toCharArray();
        for (int i=0; i<x.length() / 2; i++) {
            swap(cc, i, cc.length - i - 1);
        }
        return new String(cc);
    }

    static void swap(char[] cc, int x, int y) {
        char tmp = cc[y];
        cc[y] = cc[x];
        cc[x]  = tmp;
    }
}