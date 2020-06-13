package com.xz.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("pipeline hashCode:" + ctx.pipeline().hashCode() +
                    "HttpServerHandler hashCode:" + this.hashCode());

            System.out.println("msg 的类型:" + msg.getClass().getSimpleName());
            System.out.println("客户端的地址:" + ctx.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest) msg;
            /**
             * 浏览器会请求一次http://localhost:6668/favicon.ico 这里过滤下
             */
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("图标的请求不处理");
                return;
            }
            //返回消息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello ,客户端,我是服务器", CharsetUtil.UTF_8);
            //构造http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
