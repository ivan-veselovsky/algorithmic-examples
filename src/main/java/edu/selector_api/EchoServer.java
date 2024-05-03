package edu.selector_api;

import lombok.SneakyThrows;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class EchoServer implements AutoCloseable {

    private static final String POISON_PILL = "POISON_PILL";
    static final int PORT = 5454;

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private final AtomicBoolean stop = new AtomicBoolean();
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);

    @SneakyThrows
    public static void main(String[] args)  {
        new EchoServer().run0("localhost", PORT, () -> {});
    }

    @SneakyThrows
    public void run0(String host, int port, Runnable startedCallback) {
        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            startedCallback.run();

            final boolean synchronousMode = false;
            while (!stop.get()) {
                int selected = selector.select();
                final Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Collection<CompletableFuture<?>> futures = new ArrayList<>(selectedKeys.size());
                for (SelectionKey selectionKey: selector.selectedKeys()) {
                    if (selectionKey.isAcceptable()) {
                        acceptAndRegister();
                    }
                    if (selectionKey.isReadable()) {
                        if (synchronousMode) {
                            processRequest(selectionKey);
                        } else {
                            CompletableFuture<Void> f = CompletableFuture.runAsync(() -> processRequest(selectionKey));
                            futures.add(f);
                        }
                    }
                }
                if (!synchronousMode) {
                    CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).get();
                }
                selectedKeys.clear();
            }
        } finally {
            terminatedLatch.countDown();
        }
    }

    private void processRequest(SelectionKey selectionKey) {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        if (!answerWithEcho(buffer, selectionKey)) {
            stop.compareAndSet(false, true);
        }
    }

    @SneakyThrows
    private void join() {
        stop.compareAndSet(false, true);

        Selector selector0 = this.selector;
        if (selector0 != null) {
            selector0.wakeup();
        }

        terminatedLatch.await();
    }

    @Override
    @SneakyThrows
    public void close() {
        join();

        ServerSocketChannel serverSocket0 = this.serverSocketChannel;
        if (serverSocket0 != null) {
            serverSocket0.close();
        }
        Selector selector0 = this.selector;
        if (selector0 != null) {
            selector0.close();
        }
    }

    @SneakyThrows
    private boolean answerWithEcho(final ByteBuffer buffer, SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        buffer.clear();
        int numberOfBytesRead = socketChannel.read(buffer);

        Thread.sleep(1); // TODO: here we simulate some computational load

        if (numberOfBytesRead > 0) {
            buffer.flip();
            String message = readAsString(buffer);
            if (POISON_PILL.equals(message)) {
                socketChannel.close();
                System.out.println("STOP signalled. Exiting.");
                return false;
            } else {
                //System.out.println("message received: [" + message + "]");
                revert(buffer);
                //System.out.println("message sent: [" + readAsString(buffer) + "]");
                socketChannel.write(buffer);
            }
        } else {
            // have to do that to avoid infinite looping
            socketChannel.close();
        }
        return true;
    }

    public static String readAsString(ByteBuffer bb) {
        final int length = bb.limit() - bb.position();
        if (length <= 0) {
            return "";
        }
        char[] cc = new char[length];
        for (int i = 0; i < length; i++) {
            cc[i] = (char)bb.get(bb.position() + i);
        }
        return new String(cc);
    }

    /** Reverts the message in the byte buffer. */
    void revert(ByteBuffer bb) {
        final int length = bb.limit() - bb.position();
        if (length < 2) {
            return;
        }
        for (int i = 0; i < length / 2; i++) {
            swap(bb, bb.position() + i, bb.limit() - i - 1);
        }
    }

    private void swap(ByteBuffer bb, int x, int y) {
        byte tmp = bb.get(y);
        bb.put(y, bb.get(x));
        bb.put(x, tmp);
    }

    @SneakyThrows
    private void acceptAndRegister() {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    @SneakyThrows
    public Process start() {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = EchoServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}