package cn.iocoder.springboot.lab75.demo.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author lt-pc
 */
@Configuration
public class TestConfiguration {
    public TestConfiguration() {
        System.out.println("TestConfiguration容器启动初始化。。。");

    }
}
