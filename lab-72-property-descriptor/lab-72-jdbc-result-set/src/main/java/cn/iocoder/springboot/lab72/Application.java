package cn.iocoder.springboot.lab72;

import cn.iocoder.springboot.lab72.bean.Demo;
import cn.iocoder.springboot.lab72.utils.PropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer(){
        return new TomcatServletWebServerFactory(8081) ;
    }

    @PostConstruct
    void test() {
        Demo demo = new Demo();
        try {

            PropertyDescriptor descriptor = PropertyUtil.getPropertyDescriptor(Demo.class, "test");
            //    PropertyDescriptor descriptor = new PropertyDescriptor("test", Demo.class);
//            PropertyDescriptor descriptor = new PropertyDescriptor("test", Demo.class,
//                    "getTest",
//                    "setTest");

//            Method method = descriptor.getWriteMethod();
            Method method = descriptor.getWriteMethod();
            try {
                method.invoke(demo, "tom");
                log.info("===" + demo.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
