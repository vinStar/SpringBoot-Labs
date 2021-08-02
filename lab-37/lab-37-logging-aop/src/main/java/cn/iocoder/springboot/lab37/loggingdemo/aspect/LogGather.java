package cn.iocoder.springboot.lab37.loggingdemo.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author x
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogGather {
    /**
     * 请求标识
     */
    String tag() default "";

    /**
     * 接口描述
     */
    String description() default "";

    /**
     * 哪些异常可以不用输出
     */
    String[] excludeException() default "";
}
