package com.whhxz.dtrobot.aspect;

import com.whhxz.dtrobot.demo.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * DDAspectTest
 * Created by xuzhuo on 2018/12/17.
 */
@ContextConfiguration("classpath:application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DTAspectTest {
    @Autowired
    DemoService demoService;
    @Autowired
    ApplicationContext context;
    @Test
    public void beans()throws Exception{
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }

    @Test
    public void aspect()throws Exception{
        for (int i = 0; i < 10; i++) {
            try {
                demoService.m();

            } catch (Exception e) {
            }
            System.out.println(i);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
