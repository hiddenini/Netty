package com.xz.netty.coder;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyServerHandler extends SimpleChannelInboundHandler {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("MyServerHandler channelRead0被调用");
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是:" + buf.toString(CharsetUtil.UTF_8));

        //给客户端发送一个long
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 我是服务端", CharsetUtil.UTF_8));
    }
}
