package edu.selector_api;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static edu.selector_api.EchoServer.PORT;
import static edu.selector_api.EchoServer.readAsString;

public class EchoClient implements AutoCloseable {
    private final SocketChannel socketChannel;
    private final ByteBuffer buffer;

    @SneakyThrows
    @Override
    public void close() {
        socketChannel.close();
        buffer.clear();
    }

    EchoClient() {
        this("localhost", PORT);
    }

    @SneakyThrows
    public EchoClient(String host, int port) {
        socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        buffer = ByteBuffer.allocate(256);
    }

    @SneakyThrows
    public String sendMessage(String message) {
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();
        socketChannel.write(buffer);

        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        return readAsString(buffer);
    }

    public static void main(String[] args) {
        try (ExecutorService service = Executors.newFixedThreadPool(20)) {
            for (int t=0; t<20; t++) {
                int threadId = t;
                service.submit(() -> doJob(threadId));
            }
        }
    }

    @SneakyThrows
    private static void doJob(int threadId) {
        try (EchoClient echoClient = new EchoClient()) {
            for (int k=0; k<1000; k++) {
                String response = echoClient.sendMessage("Thread " + threadId + " , message #" + k);
                System.out.println("Response: [" + response + "]");
                Thread.sleep(10L);
            }
        }
    }
}