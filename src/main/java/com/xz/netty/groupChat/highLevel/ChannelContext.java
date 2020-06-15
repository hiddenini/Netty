package com.xz.netty.groupChat.highLevel;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelContext {
    private static Map<String, ChannelHandlerContext> map = new HashMap<>();

    private static void addChannelHandlerContext(String id, ChannelHandlerContext channelHandlerContext) {
        map.put(id, channelHandlerContext);
    }

    static void removeChannelHandlerContext(Integer id) {
        map.remove(id);
    }

    static ChannelHandlerContext getChannelHandlerContext(String id) {
        return map.get(id);
    }

    static void dealMessage(String message, ChannelHandlerContext channelHandlerContext) {
        if (!StringUtil.isNullOrEmpty(message)) {
            final MessageBean messageBean = JSON.parseObject(message, MessageBean.class);
            //拿到当前客户端的id
            String sendId = messageBean.getSendId();
            addChannelHandlerContext(sendId, channelHandlerContext);
            List<String> toIds = messageBean.getToIds();
            if (toIds != null && !StringUtil.isNullOrEmpty(toIds.get(0))) {
                toIds.stream().forEach(id -> {
                    ChannelHandlerContext sendChannelHandlerContext = getChannelHandlerContext(id);
                    if (!(sendChannelHandlerContext == null)) {
                        writeMessage(messageBean, sendChannelHandlerContext);
                    }
                });
                return;
            }
            for (String key : map.keySet()) {
                if (!key.equals(sendId)) {
                    ChannelHandlerContext channelHandlerContext1 = map.get(key);
                    writeMessage(messageBean, channelHandlerContext1);
                }
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @param ctx     客户端
     */
    static void writeMessage(MessageBean message, ChannelHandlerContext ctx) {
        String s = "[客户端]" + ctx.channel().remoteAddress() + "发送了消息" + message.getContent();
        ctx.writeAndFlush(Unpooled.copiedBuffer(s, CharsetUtil.UTF_8));
    }

}
