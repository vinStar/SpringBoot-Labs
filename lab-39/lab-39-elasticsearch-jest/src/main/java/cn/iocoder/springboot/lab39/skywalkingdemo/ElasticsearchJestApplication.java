package cn.iocoder.springboot.lab39.skywalkingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;

//@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
@SpringBootApplication
public class ElasticsearchJestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchJestApplication.class, args);
    }

}
