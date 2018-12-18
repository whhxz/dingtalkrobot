package com.whhxz.dtrobot.demo;

import com.whhxz.dtrobot.annotation.DingTalkMsg;
import org.springframework.stereotype.Component;

/**
 * DemoService
 * Created by xuzhuo on 2018/12/17.
 */
@Component
public class DemoService {
    @DingTalkMsg()
    public void m() {
        int i = 1 / 0;
    }
}
