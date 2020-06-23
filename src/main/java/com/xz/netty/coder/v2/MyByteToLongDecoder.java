package com.xz.netty.coder.v2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decode被调用");
        //因为 long 8个字节, 需要判断有8个字节，才能读取一个long
        /**
         *如果客戶端傳遞過來的數據是abcdabcdabcdabcd（16個字節）  則server的這個方法會被調用來2次,後面的MyServerHandlerV2
         *
         * 的channelRead0也會被調用2次,會往客戶端寫2次數據
         */
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
