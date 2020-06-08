package com.xz.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用bio创建一个服务端 使用telnet 127.0.0.1 6666 进行连接
 */
public class BioSever {
    public static void main(String[] args) throws Exception {
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建一个ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("server is starting");

        while (true) {
            System.out.println("线程id:" + Thread.currentThread().getId());
            System.out.println("等待客户端连接-------------");
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端-------------");
            executorService.execute(() -> {
                handle(socket);
            });
        }

    }

    private static void handle(Socket socket) {
        try {
            while (true) {
                System.out.println("handle方法 线程id:" + Thread.currentThread().getId());
                byte[] bytes = new byte[1024];
                InputStream inputStream = socket.getInputStream();
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭连接---");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
