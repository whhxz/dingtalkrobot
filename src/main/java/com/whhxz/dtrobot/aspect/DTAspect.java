package com.whhxz.dtrobot.aspect;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;
import com.google.common.util.concurrent.RateLimiter;
import com.whhxz.dtrobot.annotation.DingTalkMsg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * DDAspect
 * Created by xuzhuo on 2018/12/17.
 */
@Aspect
@Component
public class DTAspect {
    private RateLimiter rateLimiter = RateLimiter.create(5.0 / 60.0);
    private DingtalkChatbotClient client = new DingtalkChatbotClient();

    @AfterThrowing(throwing = "ex", pointcut = "@within(com.whhxz.dtrobot.annotation.DingTalkMsg) || @annotation(com.whhxz.dtrobot.annotation.DingTalkMsg)")
    public void exception(JoinPoint joinPoint, Throwable ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        DingTalkMsg dingTalkMsg = method.getAnnotation(DingTalkMsg.class);
        if (dingTalkMsg == null) {
            dingTalkMsg = (DingTalkMsg) signature.getDeclaringType().getAnnotation(DingTalkMsg.class);
        }
        boolean sign = false;
        for (Class<? extends Throwable> clazz : dingTalkMsg.sendFor()) {
            if (clazz.isInstance(ex)){
                sign = true;
                break;
            }
        }
        if (sign && rateLimiter.tryAcquire()) {
            String author = dingTalkMsg.at();
            TextMessage message = new TextMessage("@" + author + "测试");
            message.setAtMobiles(Collections.singletonList(author));
            try {
                client.send("https://oapi.dingtalk.com/robot/send?access_token=eacd878d7bb9f85bf91d9103f22cce486f536f92597e4b3d21c4ba0ca7204c77", message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
