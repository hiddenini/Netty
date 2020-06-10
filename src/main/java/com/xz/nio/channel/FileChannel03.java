package com.xz.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel 和一个ByteBuffer完成文件的拷贝
 */
public class FileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        while (true) {
            byteBuffer.clear();
            int read = fileInputStreamChannel.read(byteBuffer);
            /**
             * 如果已经读完
             */
            if (read == -1) {
                fileInputStream.close();
                fileOutputStream.close();
                break;
            }
            System.out.println("read:" + read);
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
        }
    }
}
