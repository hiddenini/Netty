package com.xz.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGathering {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        System.out.println("等待客户端连接----");
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("连接到一个客户端----");
        int size = 8;
        while (true) {
            int read = 0;
            while (read < size) {
                long l = socketChannel.read(byteBuffers);
                read += 1;
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position=" + byteBuffer.position() + "  limit=" + byteBuffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            long write = 0;
            while (write < size) {
                socketChannel.write(byteBuffers);
                write += 1;
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("read=" + read + "write=" + write + "size=" + size);
        }
    }
}
