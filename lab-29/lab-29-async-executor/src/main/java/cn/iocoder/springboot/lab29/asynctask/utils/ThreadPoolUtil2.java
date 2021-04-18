package cn.iocoder.springboot.lab29.asynctask.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolUtil2 {


    private Logger logger = LoggerFactory.getLogger(getClass());

    //specify  destroy method

    @Bean(value = "planOrderTriggerTaskExecutor", destroyMethod = "shutdown")
    public ExecutorService planOrderTaskExecutor() {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("PlanOrderTrigger-pool-trade %d ").build();
        //目前核心线程数先定在20 具体线程数看之后具体性能进行调节
        return new ThreadPoolExecutor(10, 30, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean("settleTaskExecutor")
    public ExecutorService settleTaskExecutor() {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("settleTaskExecutor-pool-trade %d ").build();
        //目前核心线程数先定在20 具体线程数看之后具体性能进行调节
        return new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean("offLineTaskExecutor")
    public ExecutorService offLineTaskExecutor() {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("offLineTaskExecutor-pool-trade %d ").build();
        //目前核心线程数先定在5 具体线程数看之后具体性能进行调节
        return new ThreadPoolExecutor(5, 10, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 定时获取配置线程池
     *
     * @return
     */
    @Bean("pullConfigScheduledExecutor")
    public ScheduledExecutorService pullConfigScheduledExecutor() {
        int cpuCoreNum = Runtime.getRuntime().availableProcessors() / 2;
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("pull-config-task-%d ").build();
        return Executors.newScheduledThreadPool(cpuCoreNum, threadFactory);
    }





}