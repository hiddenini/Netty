package com.xz.netty.groupChat.highLevel;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class GroupChatPointClient {
    private Random random = new Random();
    private String host;
    private int port;

    public GroupChatPointClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            socketChannel.pipeline().addLast(new GroupChatClientPointHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            String sendId = UUID.randomUUID().toString().replace("-", "");
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            MessageBean messageBean = new MessageBean();
            System.out.println("请输入你的id:");
            String youId = scanner.nextLine();
            messageBean.setSendId(youId);
            System.out.println("请输入想要聊天的人的id:");
            String toId = scanner.nextLine();
            messageBean.setToIds(Arrays.asList(toId));

            System.out.println("请输入聊天的内容:");
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                System.out.println("输入的字符串是:" + s);
                messageBean.setContent(s);
                System.out.println("准备发送消息:" + JSON.toJSONString(messageBean));
                channel.writeAndFlush(JSON.toJSONString(messageBean) + "\r\n");
            }

        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new GroupChatPointClient("127.0.0.1", 7000).run();
    }
}
