package edu.selector_api;

import lombok.SneakyThrows;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class EchoServer implements AutoCloseable {

    private static final String POISON_PILL = "POISON_PILL";
    static final int PORT = 5454;

    private Selector selector;
    private ServerSocketChannel serverSocket;
    private final AtomicBoolean stop = new AtomicBoolean();
    private final AtomicBoolean terminated = new AtomicBoolean();

    @SneakyThrows
    public static void main(String[] args)  {
        new EchoServer().run(args);
    }

    @SneakyThrows
    public void run(String... args) {
        try {
            selector = Selector.open();

            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", PORT));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            final ByteBuffer buffer = ByteBuffer.allocate(256);
            while (!stop.get()) {
                int selected = selector.select();
                System.out.println("selected: " + selected);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }
                    if (key.isReadable()) {
                        if (!answerWithEcho(buffer, key)) {
                            stop.compareAndSet(false, true);
                        }
                    }
                    buffer.clear();

                    it.remove();
                }
            }
        } finally {
            terminated.compareAndSet(false, true);
        }
    }

    @SneakyThrows
    private void join() {
        stop.compareAndSet(false, true);
        do {
            Thread.sleep(5);
        } while (!terminated.get());
    }

    @Override
    @SneakyThrows
    public void close() {
        join();

        ServerSocketChannel serverSocket0 = this.serverSocket;
        if (serverSocket0 != null) {
            serverSocket0.close();
        }
        Selector selector0 = this.selector;
        if (selector0 != null) {
            selector0.close();
        }
    }

    @SneakyThrows
    private boolean answerWithEcho(final ByteBuffer buffer, SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel)key.channel();
        buffer.clear();
        int numberOfBytesRead = socketChannel.read(buffer);
        if (numberOfBytesRead > 0) {
            buffer.flip();
            String message = readAsString(buffer);
            if (POISON_PILL.equals(message)) {
                socketChannel.close();
                System.out.println("STOP signalled. Exiting.");
                return false;
            } else {
                System.out.println("message received: [" + message + "]");
                revert(buffer);
                System.out.println("message sent: [" + readAsString(buffer) + "]");
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
    private void register(Selector selector, ServerSocketChannel serverSocketChannel) {
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