package cn.iocoder.springboot.lab73.demo.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Classname ThreadPoolConfig
 * @Date 2020/3/2 15:27
 * @Created by mengdesheng
 */
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

    private static final int CORE_POOL_SIZE = CPU_COUNT / 2 + 1;
    /**
     * 线程池最大线程数 = CPU核心数 + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 非核心线程闲置时间 = 超时60s
     */
    private static final int KEEP_ALIVE = 0;

    @Bean(name = "export")
    public ExecutorService commonThreadPool() {
        log.info("debug in thread pool config , method common pool executor .thread pool begin create");
        //定义线程名称
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("export-%d").build();

        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }


}
