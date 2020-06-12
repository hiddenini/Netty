package com.xz.netty.simpleStart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 注释链接:http://www.imooc.com/article/details/id/51371
 */
public class ServerHandlerCustomTask extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是:" + ctx.channel().remoteAddress());

        /**
         * 1--用户自定义的普通任务,跟下execute() 可以看到是被
         *
         * 如果是当前reactor线程则直接add到了SingleThreadEventExecutor中的taskQueue(LinkedBlockingQueue<Runnable>)中.
         *
         * 否则尝试启动线程(里面保证了只有一个线程)再将任务添加到taskQueue中去
         *
         */
        ctx.channel().eventLoop().execute(() -> {
            System.out.println("我是一个很耗时的任务-----");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("耗时任务结束-----");
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端,我是服务器", CharsetUtil.UTF_8));
        });

        /**
         * 2--用户自定义的定时任务,跟下schedule() 可以看到是被
         *
         * 如果是当前reactor的线程则
         *
         * add到了AbstractScheduledEventExecutor中的taskQueue(PriorityQueue<ScheduledFutureTask<?>>)中
         *
         * 这里为什么要使用优先级队列，而不需要考虑多线程的并发？ 因为我们现在讨论的场景，调用链的发起方是reactor线程，不会存在多线程并发
         *
         * 如果是非reactor线程
         *
         * 如果是在外部线程调用schedule，netty将添加定时任务的逻辑封装成一个普通的task，这个task的任务是添加[添加定时任务]的任务，而不是添加定时任务，其实也就是第二种场景
         *
         * 1.若干时间后执行一次
         * 2.每隔一段时间执行一次
         * 3.每次执行结束，隔一定时间再执行一次
         *
         *if (periodNanos == 0) 对应 若干时间后执行一次 的定时任务类型，执行完了该任务就结束了。
         *
         * 否则，进入到else代码块，先执行任务，然后再区分是哪种类型的任务，periodNanos大于0，表示是以固定频率执行某个任务，和任务的持续时间无关
         *
         * 然后，设置该任务的下一次截止时间为本次的截止时间加上间隔时间periodNanos，否则，就是每次任务执行完毕之后，间隔多长时间之后再次执行。
         *
         * 截止时间为当前时间加上间隔时间，-p就表示加上一个正的间隔时间，最后，将当前任务对象再次加入到队列，实现任务的定时执行
         *
         *
         */
        ctx.channel().eventLoop().schedule(() -> {
            System.out.println("我是一个很耗时的任务-----");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("耗时任务结束-----");
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端,我是服务器", CharsetUtil.UTF_8));
        }, 5, TimeUnit.SECONDS);


        /**
         *3--非当前reactor线程调用channel的各种方法
         *
         *上面一种情况在push系统中比较常见，一般在业务线程里面，根据用户的标识，找到对应的channel引用，然后调用write类方法向该用户推送消息，就会进入到这种场景
         *
         * 外部线程在调用write的时候，executor.inEventLoop()会返回false，直接进入到else分支
         *
         * 将write封装成一个WriteTask（这里仅仅是write而没有flush，因此flush参数为false）, 然后调用 safeExecute方法
         *
         * 接下来的调用链就进入到第一种场景了
         *
         *
         */
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端,我是服务器", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
