package edu.selector_api;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/** Example from https://www.baeldung.com/java-nio-selector */
public class SelectorTrial {

    void foo() throws IOException  {
        try (Selector selector = Selector.open();
             SelectableChannel channel = ServerSocketChannel.open(StandardProtocolFamily.INET)) {

            channel.configureBlocking(false);

            SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);
            int ops = selectionKey.readyOps();
            boolean readable = selectionKey.isReadable();

            SelectableChannel ch2 = selectionKey.channel();
            assert channel == ch2;

            selectionKey.attach("foo");
            int channels;
            while (true) {
                channels = selector.select();
                if (channels != 0) {
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                }
            }
        }
    }
}
