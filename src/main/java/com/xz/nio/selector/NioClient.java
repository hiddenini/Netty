package com.xz.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7777);
        if (!socketChannel.connect(inetSocketAddress)) {
            if (!socketChannel.finishConnect()) {
                System.out.println("连接中,客户端可以做其他事情---");
            }
        }
        //连接上之后发送内容
        String str = "hello netty";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        int write = socketChannel.write(byteBuffer);
        System.in.read();
    }
}
