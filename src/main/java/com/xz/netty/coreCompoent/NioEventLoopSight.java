package com.xz.netty.coreCompoent;

public class NioEventLoopSight {
    /**
     * 1.首先轮询注册到reactor线程对用的selector上的所有的channel的IO事件   select(wakenUp.getAndSet(false)
     *
     *2.处理产生网络IO事件的channel           processSelectedKeys();
     *
     *3.处理任务队列      runAllTasks();
     *
     *
     *
     *
     * 1--select
     *
     * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6595055  jdk nio cpu100% bug
     *
     * 若Selector的轮询结果为空，也没有wakeup或新消息处理，则发生空轮询，CPU使用率100%
     *
     * while(true){
     *      //doNothing
     * }
     *
     * 在部分Linux的2.6的kernel中，poll和epoll对于突然中断的连接socket会对返回的eventSet事件集合置为POLLHUP，也可能是POLLERR，eventSet事件集合发生了变化，这就可能导致Selector会被唤醒。
     *
     * rebuildSelector();
     *
     *netty 会在每次进行 selector.select(timeoutMillis) 之前记录一下开始时间currentTimeNanos，在select之后记录一下结束时间
     *
     * 判断select操作是否至少持续了timeoutMillis秒（这里将time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) >= currentTimeNanos
     *
     * 改成time - currentTimeNanos >= TimeUnit.MILLISECONDS.toNanos(timeoutMillis)或许更好理解一些）
     *
     * 如果持续的时间大于等于timeoutMillis，说明就是一次有效的轮询，重置selectCnt标志，否则，表明该阻塞方法并没有阻塞这么长时间
     *
     * 可能触发了jdk的空轮询bug，当空轮询的次数超过一个阀值的时候，默认是512(int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);)
     *
     * 就开始重建selector
     *
     *
     * 2-- processSelectedKeys();
     *
     *    private Set<SelectionKey> publicSelectedKeys;
     *
     *    protected Set<SelectionKey> selectedKeys = new HashSet();
     *
     *    使用反射将这2个字段替换成了SelectedSelectionKeySet  netty自定义的额set 底层是一个数组实现 add方法的时间复杂度是O(1)(老版本是2个数组后面修改为1个数组了)
     *
     *      processSelectedKeysOptimized()中主要做了以下几件事情
     *      1--取出SelectionKey   SelectionKey k = selectedKeys.keys[i]
     *
     *      2--拿到channel Object a = k.attachment();
     *
     *      在AbstractNioChannel中的 doRegister()方法中将this(即AbstractNioChannel当做attachment)   javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
     *
     *      AbstractNioChannel内部有一个jdk下面的SelectableChannel
     *
     *      NioTask用的比较少
     *
     *      if (a instanceof AbstractNioChannel) {
     *           processSelectedKey(k, (AbstractNioChannel) a);
     *             } else {
     *           @SuppressWarnings("unchecked")
     *             NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
     *            processSelectedKey(k, task);
     *             }
     *
     *
     *      接下里判断是否需要再次来轮训
     *
     *      if (needsToSelectAgain)         needsToSelectAgain初始化为false  在AbstractNioChannel中的doDeregister()  -->NioEventLoop中的cancel方法中
     *
     *      if (cancelledKeys >= CLEANUP_INTERVAL) 则被置为true CLEANUP_INTERVAL=256
     *
     *      也就是说，对于每个NioEventLoop而言，每隔256个channel从selector上移除的时候，就标记 needsToSelectAgain 为true
     *
     *      将selectedKeys(SelectedSelectionKeySet)的内部数组全部清空，方便被jvm垃圾回收，然后重新调用selectAgain重新填装一下 selectionKey调用selector.selectNow();
     *
     *
     *
     *
     *
     */
}
