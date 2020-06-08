package com.xz.nio.buffer;

import java.nio.ByteBuffer;

/**
 * put和get时的类型需要保持一致,否则可能导致java.nio.BufferOverflowException
 */
public class UnderFlowExceptionExample {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('中');
        byteBuffer.putShort((short) 8);
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
    }
}
