package cn.iocoder.springboot.lab29.asynctask.service;

import cn.iocoder.springboot.lab29.asynctask.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DemoService demoService;


    //  get the result
    @Test
    public void task05() throws ExecutionException, InterruptedException {
        long now = System.currentTimeMillis();
        logger.info("[task05][开始执行]");

        // 执行任务
        Future<Object> execute03Result = demoService.executeGet03();

        Object obj = execute03Result.get();
//
//
//        logger.info("obj is : {}", obj);
        logger.info("[task03][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
    }


    // don't get the result
    // if don't set   ThreadPoolUtil > @PreDestroy > awaitTerminate ,will destroy immediately
    //
    @Test
    public void task055() throws ExecutionException, InterruptedException {
        long now = System.currentTimeMillis();
        logger.info("[task05][开始执行]");

        // 执行任务
        Future<Object> execute03Result = demoService.executeGet03();

        logger.info("[task03][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
    }

}
