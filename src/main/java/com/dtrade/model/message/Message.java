package com.dtrade.model.message;

import lombok.Data;

@Data
public class Message {

    private String channel;

    private Object entity;

    public static Message build(String channel, Object entity){
        Message message = new Message();
        message.setChannel(channel);
        message.setEntity(entity);
        return message;
    }
}
