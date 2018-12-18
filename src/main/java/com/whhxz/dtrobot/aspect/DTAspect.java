package com.whhxz.dtrobot.aspect;

import com.whhxz.dtrobot.annotation.DingTalkMsg;
import com.whhxz.dtrobot.message.ChatbotClient;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * DDAspect
 * Created by xuzhuo on 2018/12/17.
 */
@Aspect
@Component
public class DTAspect {
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
            if (clazz.isInstance(ex)) {
                sign = true;
                break;
            }
        }
        if (sign) {
            String author = dingTalkMsg.at();
            List<String> ats = new ArrayList<>(1);
            ats.add(author);
            ChatbotClient.send("测试", ats);
        }
    }
}
