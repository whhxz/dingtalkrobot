package com.whhxz.dtrobot.message;

import com.dingtalk.chatbot.message.Message;

/**
 * BotSendMessage
 * Created by xuzhuo on 2018/12/18.
 */
public class ChatbotMessage {
    private String webhook;
    private Message message;

    public ChatbotMessage(String webhook, Message message) {
        this.webhook = webhook;
        this.message = message;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
