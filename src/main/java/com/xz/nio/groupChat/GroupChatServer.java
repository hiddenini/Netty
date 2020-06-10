package com.xz.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        SocketChannel accept = null;
        SelectionKey key = null;
        try {
            while (true) {
                int select = selector.select(2000);
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        if (key.isAcceptable()) {
                            accept = listenChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + "上线了");
                        }
                        if (key.isReadable()) {
                            readData(key);
                        }

                        iterator.remove();
                    }
                } else {
                    //System.out.println("服务端等待中-----");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = channel.read(byteBuffer);
            String msg = null;
            if (read > 0) {
                msg = new String(byteBuffer.array());
                System.out.println("from 客户端:" + channel.getRemoteAddress() + msg);
            }
            //向其他客户端转发消息
            sendInfoToOtherClients(msg, channel);
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void sendInfoToOtherClients(String mes, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中----");
        //遍历所有注册到selector上面的key并排除自己
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(mes.getBytes());
                dest.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
