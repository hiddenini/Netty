package com.xz.netty.coreCompoent;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnPooledSight {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println(buffer.capacity());

        /**
         * netty提供的ByteBuf 不需要进行flip()
         *
         * 底层维护了writerIndex  和 readerIndex 以及capacity
         */

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.readByte();
            if (i == 5) {
                buffer.writeByte(i*100);
            }
        }
    }
}
