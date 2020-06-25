package com.xz.netty.tcp.solution2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandlerS2 extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("client received message length:     " + len + "  content:" + new String(content, Charset.forName("utf-8")));
        System.out.println("client receive message count:" + (++count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送5条数据 hello server how are you doing today? 编号
        for (int i = 0; i < 5; i++) {
            String message = "hello server how are you doing today?";
            byte[] content = message.getBytes(Charset.forName("utf-8"));
            int length = content.length;

            //創建自定義協議包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(length);
            ctx.writeAndFlush(messageProtocol);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
