package com.xz.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用MappedByteBuffer修改文件内容(修改完 show in explorer查看效果)
 * <p>
 * 可以在堆外内存
 */
public class NioMappedByteBuffer {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'A');
        mappedByteBuffer.put(2, (byte) 'B');
        //mappedByteBuffer.put(5, (byte) 'C'); IndexOutOfBoundsException
        randomAccessFile.close();
        System.out.println("修改成功!");
    }
}
