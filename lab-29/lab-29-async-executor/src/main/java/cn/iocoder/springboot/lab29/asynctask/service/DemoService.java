package cn.iocoder.springboot.lab29.asynctask.service;

import cn.iocoder.springboot.lab29.asynctask.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Service
public class DemoService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    ThreadPoolUtil threadPoolUtil;


    public Future<Object> executeGet03() {

        // shutdown can't start
        threadPoolUtil.demoThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("execute  ");
                sleep(5);

            }
        });


        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return threadPoolUtil.boomThreadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                logger.info(methodName + " begin execute");
                sleep(5);
                logger.info(methodName + " executed end");
                return "我是返回值";
            }
        });


    }


    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
