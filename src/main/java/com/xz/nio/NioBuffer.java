package com.xz.nio;

import java.nio.IntBuffer;

/**
 * Nio中的buffer的简单使用
 * <p>
 * buffer中比较重要的概念是
 * 1--position 下一个要被操作的索引 每次进行读写时都会被改变,为下次读写做准备
 * 2--limit    最大的索引  缓冲区的当前终点,读写操作不能超过该位置,可以被修改
 * 3--capacity 容量 初始化时被设置且不能修改
 */
public class NioBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        /**
         * 进行debug可以看到 初始化时position=0;  limit=10; capacity=10;
         * 赋值过程中position++;
         * 赋值完成后 position=5;  limit=10; capacity=10;
         */
        for (int i = 0; i < (intBuffer.capacity() >> 1); i++) {
            intBuffer.put(2 * i);
        }
        /**
         * 将buffer进行读写切换,很重要
         * 切换后 position=0;  limit=position(5); capacity=10;
         */
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
