package com.whhxz.dtrobot.message;

import java.util.HashMap;
import java.util.Map;

/**
 * BotConfig
 * Created by xuzhuo on 2018/12/18.
 */
public class BotConfig {
    /**
     * 默认发送
     */
    private String defaultToken;
    /**
     * 联系人
     */
    private Map<String, String> contacts = new HashMap<>();

    public BotConfig(String defaultToken) {
        this.defaultToken = defaultToken;
    }

    public String getDefaultToken() {
        return defaultToken;
    }

    public void setDefaultToken(String defaultToken) {
        this.defaultToken = defaultToken;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }
}
