package com.xz.netty.tcp.demonstration;

public class Test {
    public static void main(String[] args) {
        String str = "hello,server TCP 是基于流传输的协议，请求数据在其传输的过程中是没有界限区分，所以我们在读取请求的时候，不一定能获取到一个完整的数据包 0hello,server TCP 是基于流传输的协议，请求数据在其传输的过程中是没有界限区分，所以我们在读取请求的时候，不一定能获取到一个完整的数据包 1hello,server TCP 是基于流传输的协议，请求数据在其传输的过程中是没有界限区分，所以我们在读取请求的时候，不一定能获取到一个完整的数据包 2hello,server TCP 是基于流传输的协议，请求数据在其传输的过程中是没有界限区分，所以我们在读取请求的时候，不一定能获取到一个完整的数据包 3hello,server TCP 是基于流传输的协议，请求数据在其传输的过程中是没有界限区分，所以我们在读取请求的时候，不一定能获取到一个完整的数据包 4hello,server TCP 是基于流传输的协议，请求数据\n";
        System.out.println(str.getBytes().length);
    }
}
