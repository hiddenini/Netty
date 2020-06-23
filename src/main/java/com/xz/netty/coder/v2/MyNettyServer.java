package com.xz.netty.coder.v2;

import com.xz.netty.coder.v1.MyAnotherDecoder;
import com.xz.netty.coder.v1.MyDecoder;
import com.xz.netty.coder.v1.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("myDecoder", new MyByteToLongDecoder());
                            //入站的handler进行解码 MyByteToLongDecoder
                            pipeline.addLast(new MyByteToLongDecoder());
                            //出站的handler进行编码
                            pipeline.addLast(new MyLongToByteEncoder());
                            //自定义的handler 处理业务逻辑
                            pipeline.addLast(new MyServerHandlerV2());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
