package com.whhxz.dtrobot.message;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.Message;
import com.dingtalk.chatbot.message.TextMessage;
import com.google.common.util.concurrent.RateLimiter;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ChatbotClient
 * Created by xuzhuo on 2018/12/18.
 */
public class ChatbotClient {
    private static RateLimiter rateLimiter = RateLimiter.create(5.0 / 60.0);
    private static DingtalkChatbotClient client = new DingtalkChatbotClient();
    private static BotConfig botConfig = null;
    private static String defaultWebhook = null;
    private static final String baseUrl = "https://oapi.dingtalk.com/robot/send?access_token=";
    private static final LinkedBlockingQueue<ChatbotMessage> queue = new LinkedBlockingQueue<>();
    private static final Object obj = new Object();
    private static final int MAX_ERROR = 5;

    public static void send(String msg) {
        send(new ChatbotMessage(defaultWebhook, new TextMessage(msg)));
    }

    public static void send(String msg, List<String> ats) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ats.size(); i++) {
            String mobile = botConfig.getContacts().get(ats.get(i));
            if (mobile != null) {
                ats.set(i, mobile);
            }
            sb.append("@").append(ats.get(i));
        }
        sb.append(msg);
        TextMessage message = new TextMessage(sb.toString());
        message.setAtMobiles(ats);
        send(new ChatbotMessage(defaultWebhook, message));
    }

    public static void send(Message message) {
        send(new ChatbotMessage(defaultWebhook, message));
    }

    public static void send(String accessToken, Message message) {
        send(new ChatbotMessage(baseUrl + accessToken, message));
    }

    private static void send(ChatbotMessage message) {
        if (rateLimiter.tryAcquire()) {
            try {
                queue.put(message);
                synchronized (obj) {
                    obj.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init(BotConfig botConfig) {
        ChatbotClient.botConfig = botConfig;
        defaultWebhook = baseUrl + botConfig.getDefaultToken();
        start();
    }

    private static void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int error = 0;
                while (true) {
                    try {
                        ChatbotMessage message = queue.peek();
                        if (message == null) {
                            synchronized (obj) {
                                obj.wait();
                            }
                            continue;
                        }
                        client.send(message.getWebhook(), message.getMessage());
                        queue.poll();
                    } catch (Exception e) {
                        if (++error > MAX_ERROR) {
                            error = 0;
                            queue.poll();
                        }
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
