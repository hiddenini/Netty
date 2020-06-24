package com.xz.netty.tcp.solution1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandlerS1 extends SimpleChannelInboundHandler<String> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client receives message: " + msg.trim());
        System.out.println("服务器接收到消息量=" + (++this.count));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据 hello,server xxx 编号
        for (int i = 0; i < 10; ++i) {
            ctx.writeAndFlush("hello,server TCP 是基于流传输的协议，请求数据在其传输的过程中是没有界限区分，所以我们在读取请求的时候，不一定能获取到一个完整的数据包");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
