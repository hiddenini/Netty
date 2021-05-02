package com.xz.netty.allocator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class Scratch {
    public static void main(String[] args) {
        int page = 1024 * 8;
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        //allocateNormal()
        //allocator.directBuffer(2 * page);

        //分配一个16字节的内存 allocateTiny()
        ByteBuf byteBuf = allocator.directBuffer(16);

        byteBuf.release();
    }
}
