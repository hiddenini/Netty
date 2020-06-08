package com.xz.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel和transFrom完成文件的拷贝
 */
public class FileChannel04 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("lf.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("lf1.jpg");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        fileOutputStreamChannel.transferFrom(fileInputStreamChannel, 0, fileInputStreamChannel.size());
        fileInputStreamChannel.close();
        fileInputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
