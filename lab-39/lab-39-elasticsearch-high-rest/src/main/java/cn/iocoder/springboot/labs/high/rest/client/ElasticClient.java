package cn.iocoder.springboot.labs.high.rest.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticClient {
    private String host = "127.0.0.1";
    private int port = 9200;

    /**
     * 获取Rest高级客户端
     *
     * @return
     */
    @Bean
    public RestHighLevelClient getRestHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(builder);
    }

    /**
     * 获取Rest低级客户端
     *
     * @return
     */
    @Bean
    public RestClient getRestClient() {
        RestClient build = RestClient.builder(new HttpHost(host)).build();
        return build;
    }
}
