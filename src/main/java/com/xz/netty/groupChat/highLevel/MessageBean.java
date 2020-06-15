package com.xz.netty.groupChat.highLevel;

import java.util.List;

public class MessageBean {
    private String sendId;
    /**
     * 发给哪些人
     */
    private List<String> toIds;

    /**
     * 发送的内容
     */
    private String content;

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public List<String> getToIds() {
        return toIds;
    }

    public void setToIds(List<String> toIds) {
        this.toIds = toIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
