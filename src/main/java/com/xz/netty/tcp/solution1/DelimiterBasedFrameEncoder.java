package com.xz.netty.tcp.solution1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 使用DelimiterBasedFrameDecoder 和自定义的DelimiterBasedFrameEncoder 解决粘包拆包问题每条消息的结尾标志为delimiter
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {

    private String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        ctx.writeAndFlush(Unpooled.wrappedBuffer((msg + delimiter).getBytes()));
    }
}
