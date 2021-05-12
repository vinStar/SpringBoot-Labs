package cn.iocoder.springboot.lab29.asynctask.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.*;


@Configuration
@Slf4j
public class ThreadPoolConfig {


    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数 = CPU核心数 / 2 + 1
     */

    private static final int CORE_POOL_SIZE = CPU_COUNT / 2 + 6;
    /**
     * 线程池最大线程数 = CPU核心数 + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT + 10;
    /**
     * 非核心线程闲置时间 = 超时60s
     */
    private static final int KEEP_ALIVE = 60;

    @Bean(name = "task-add", destroyMethod = "shutdown")
    public ThreadPoolExecutor commonThreadPool() {

        log.info("debug in thread pool config , method common pool executor .thread pool begin create");
        //定义线程名称
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("task-add-%d").build();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(5000), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        executorService.prestartAllCoreThreads();
        return threadPoolExecutor;
    }

    @Resource(name = "task-add")
    ThreadPoolExecutor executorService;

    @PreDestroy
    public void destroy() {

        // 相比 destroyMethod 可以控制等待时间记录超时日志日志
        executorService.shutdown();
        log.info("{} shutdown", commonThreadPool().toString());
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("{} shutdown", e);
        }
    }


}
