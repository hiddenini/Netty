package com.xz.netty.tcp.solution1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * TCP属于传输层的协议，传输层除了有TCP协议外还有UDP协议。
 * <p>
 * 那么UDP是否会发生粘包或拆包的现象呢？答案是不会。
 * <p>
 * UDP是基于报文发送的，从UDP的帧结构可以看出，在UDP首部采用了16bit来指示UDP数据报文的长度
 * <p>
 * 因此在应用层能很好的将不同的数据报文区分开，从而避免粘包和拆包的问题。
 * <p>
 * 而TCP是基于字节流的，虽然应用层和TCP传输层之间的数据交互是大小不等的数据块
 * <p>
 * 但是TCP把这些数据块仅仅看成一连串无结构的字节流，没有边界；另外从TCP的帧结构也可以看出
 * <p>
 * 在TCP的首部没有表示数据长度的字段，基于上面两点，在使用TCP传输数据时，才有粘包或者拆包现象发生的可能。
 * <p>
 * <p>
 * <p>
 * TCP协议：面向连接的可靠传输协议。利用TCP进行通信时，首先要通过三步握手，以建立通信双方的连接。TCP提供了数据的确认和数据重传的机制，保证发送的数据一定能到达通信的对方。
 * <p>
 * UDP协议：是无连接的，不可靠的传输协议。采用UDP进行通信时不用建立连接，可以直接向一个IP地址发送数据，但是不能保证对方是否能收到。
 * <p>
 * <p>
 * <p>
 * 发生TCP粘包或拆包有很多原因，现列出常见的几点
 * 1、要发送的数据大于TCP发送缓冲区剩余空间大小，将会发生拆包。
 * 2、待发送数据大于MSS（最大报文长度），TCP在传输前将进行拆包。
 * 3、要发送的数据小于TCP发送缓冲区的大小，TCP将多次写入缓冲区的数据一次发送出去，将会发生粘包。
 * 4、接收数据端的应用层没有及时读取接收缓冲区中的数据，将发生粘包。
 * <p>
 * <p>
 * 解决粘包拆包的常见方案
 * <p>
 * 1--客户端在发送数据包的时候，每个包都固定长度，比如1024个字节大小，如果客户端发送的数据长度不足1024个字节，则通过补充空格的方式补全到指定长度；
 * 2--客户端在每个包的末尾使用固定的分隔符，例如\r\n，如果一个包被拆分了，则等待下一个包发送过来之后找到其中的\r\n，然后对其拆分后的头部部分与前一个包的剩余部分进行合并，这样就得到了一个完整的包；
 * 3--将消息分为头部和消息体，在头部中保存有当前整个消息的长度，只有在读取到足够长度的消息之后才算是读到了一个完整的消息；
 * 4--通过自定义协议进行粘包和拆包的处理。
 */

/**
 * 演示tcp的粘包和拆包
 */
public class MyServerHandlerS1 extends SimpleChannelInboundHandler<String> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server receives message: " + msg.trim());
        System.out.println("服务器接收到消息量=" + (++this.count));
        ctx.writeAndFlush("hello client 操作系统在发送TCP数据的时候，底层会有一个缓冲区，例如1024个字节大小 ");
    }
}
