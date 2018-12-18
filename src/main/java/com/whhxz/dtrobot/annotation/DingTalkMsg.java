package com.whhxz.dtrobot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DingDingException
 * Created by xuzhuo on 2018/12/17.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DingTalkMsg {
    /**
     * 指定抛出异常
     *
     * @return
     */
    Class<? extends Throwable>[] sendFor() default {Exception.class};

    /**
     * '@'钉钉登陆手机号
     * @return
     */
    String at() default "";
}
