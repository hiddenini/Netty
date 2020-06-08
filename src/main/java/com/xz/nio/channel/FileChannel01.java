package com.xz.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel 和ByteBuffer 将字符串写入到文件中
 */
public class FileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "hello netty";
        FileOutputStream fileOutputStream = new FileOutputStream("1.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
