package com.xz.netty.coder.v2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


public class MyClientHandlerV2 extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("服务器的ip=" + ctx.channel().remoteAddress());
        System.out.println("收到服务器消息=" + msg);

    }

    //重写channelActive 发送数据

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        //ctx.writeAndFlush(Unpooled.copiedBuffer(""))
        //ctx.writeAndFlush(123456L); //发送的是一个long

        //分析
        //1. "abcdabcdabcdabcd" 是 16个字节
        //2. 该处理器的前一个handler 是  MyLongToByteEncoder
        //3. MyLongToByteEncoder 父类  MessageToByteEncoder
        //4. 父类  MessageToByteEncoder
        /*

         public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf buf = null;
        try {
            if (acceptOutboundMessage(msg)) { //判断当前msg 是不是应该处理的类型，如果是就处理，不是就跳过encode
            }
        4. 因此我们编写 Encoder 是要注意传入的数据类型和处理的数据类型一致
        */


        /**
         * 如果发送的字节数是大于8小于16(eg abcdabcdabcdabc)则服务器端只会接受到前面8个字节,剩下的7个字节应该是等待后续再有数据过去才会被读取到
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));

    }
}
