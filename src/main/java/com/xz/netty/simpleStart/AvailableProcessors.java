package com.xz.netty.simpleStart;

import io.netty.util.NettyRuntime;

public class AvailableProcessors {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors());
    }
}
