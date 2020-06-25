package com.xz.netty.tcp.solution2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;


public class MyServerHandlerS2 extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("server received message length:     " + len + "  content:" + new String(content, Charset.forName("utf-8")));
        System.out.println("server receive message count:" + (++count));

        String response = UUID.randomUUID().toString();
        byte[] bytes = response.getBytes("utf-8");
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(bytes.length);
        messageProtocol.setContent(bytes);
        ctx.writeAndFlush(messageProtocol);
    }
}
