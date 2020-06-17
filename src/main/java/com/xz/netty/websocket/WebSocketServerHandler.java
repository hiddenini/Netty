package com.xz.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import javax.xml.soap.Text;
import java.time.LocalDateTime;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服務端接受到消息:" + msg.text());
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("hello client i am server and i have received your message :" + msg.text() + "time" + LocalDateTime.now());
        ctx.channel().writeAndFlush(textWebSocketFrame);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客戶端:" + ctx.channel().remoteAddress() + "上綫");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客戶端:" + ctx.channel().remoteAddress() + "離綫");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
