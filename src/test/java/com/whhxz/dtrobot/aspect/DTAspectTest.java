package com.whhxz.dtrobot.aspect;

import com.whhxz.dtrobot.demo.DemoService;
import com.whhxz.dtrobot.message.BotConfig;
import com.whhxz.dtrobot.message.ChatbotClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * DDAspectTest
 * Created by xuzhuo on 2018/12/17.
 */
@ContextConfiguration("file:src/test/resources/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DTAspectTest {
    @Autowired
    DemoService demoService;
    @Autowired
    ApplicationContext context;

    @Before
    public void init() {
        BotConfig config = new BotConfig("eacd878d7bb9f85bf91d9103f22cce486f536f92597e4b3d21c4ba0ca7204c77");
        config.setContacts(Collections.singletonMap("xuzhuo", "18163315673"));
        ChatbotClient.init(config);
    }

    @Test
    public void beans() throws Exception {

        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }

    @Test
    public void aspect() throws Exception {
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
