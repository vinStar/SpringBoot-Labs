package cn.iocoder.springboot.lab29.asynctask.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //核心线程数
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    //最大线程数
    private static int maxPoolSize = corePoolSize + 1;

    //空闲线程存活时间
    private static int keepAliveTime = 30;

    //阻塞队列长度
    private static int queueCapacity = 1024;

    final ThreadFactory boomThreadFactory = new ThreadFactoryBuilder().setNameFormat("boom-task %d ").build();


    //线程池
    public ThreadPoolExecutor boomThreadPool = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(queueCapacity),
            boomThreadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy());


    final ThreadFactory demoThreadFactory = new ThreadFactoryBuilder().setNameFormat("dmeo-task %d ").build();

    //线程池
    public ThreadPoolExecutor demoThreadPool = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(queueCapacity),
            demoThreadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy());

    @PreDestroy
    public void destroy() {
        logger.info("PreDestroy");
        boomThreadPool.shutdown();

        try {
            //time not enough will not execute success
            if (!boomThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                logger.error("shutdown fail");
            } else {
                logger.info("shutdown success");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
