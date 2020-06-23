package com.xz.netty.coder.v1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("ToIntegerDecoder decode 被调用");
        /**
         * fireXXX 将数据传递下下一个Handler
         */
        ctx.fireChannelRead(in);
    }
}
