package cn.iocoder.springboot.labs.high.rest.controller;


import cn.iocoder.springboot.labs.high.rest.client.ElasticClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lt-pc
 */
@Slf4j
@RestController
public class HomeController {


    @Autowired
    ElasticClient elasticClient;


    @GetMapping("/add1")
    public void add1() {
        RestHighLevelClient client = elasticClient.getRestHighLevelClient();
        //Json字符串作为数据源
        IndexRequest indexRequest1 = new IndexRequest(
                "test"
                //  "book",
                //        "3"
        );
        String jsonString = "{" +
                "\"name\":\"生命的诞生\"," +
                "\"type\":\"科学\"," +
                "\"price\":170.10 ," +
                "\"tags\":[1,2,3]" +
                "}";


        // jsonString = "{'name':'name','tyep':'科学','tags':['学校','工厂']}";


        indexRequest1.source(jsonString, XContentType.JSON);
        indexRequest1.id("1");

        try {

            client.index(indexRequest1, RequestOptions.DEFAULT);

//            ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
//                @Override
//                public void onResponse(IndexResponse indexResponse) {
//                    log.info("indexResponse succeed!");
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    log.info("indexResponse failed!");
//                }
//            };
//
//            jsonString = "{" +
//                    "\"name\":\"生命的诞生\"," +
//                    "\"type\":\"科学\"," +
//                    "\"price\":170.11" +
//                    "}";
//
//            indexRequest1.source(jsonString, XContentType.JSON);
//            indexRequest1.id("2");
//
//            client.indexAsync(indexRequest1, RequestOptions.DEFAULT, listener);


          //  client.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/add2")
    public void add2() {
        RestHighLevelClient client = elasticClient.getRestHighLevelClient();
        //Json字符串作为数据源
        IndexRequest indexRequest1 = new IndexRequest(
                "test"
                //  "book",
                //        "3"
        );
        String jsonString = "{" +
                "\"name\":\"生命的诞生\"," +
                "\"type\":\"科学\"," +
                "\"price\":170.10 ," +
                "\"tags\":[1,2]" +
                "}";


        // jsonString = "{'name':'name','tyep':'科学','tags':['学校','工厂']}";


        indexRequest1.source(jsonString, XContentType.JSON);
        indexRequest1.id("1");

        try {

            client.index(indexRequest1, RequestOptions.DEFAULT);

//            ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
//                @Override
//                public void onResponse(IndexResponse indexResponse) {
//                    log.info("indexResponse succeed!");
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    log.info("indexResponse failed!");
//                }
//            };
//
//            jsonString = "{" +
//                    "\"name\":\"生命的诞生\"," +
//                    "\"type\":\"科学\"," +
//                    "\"price\":170.11" +
//                    "}";
//
//            indexRequest1.source(jsonString, XContentType.JSON);
//            indexRequest1.id("2");
//
//            client.indexAsync(indexRequest1, RequestOptions.DEFAULT, listener);


        //    client.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @GetMapping("/add3")
    public void add3() {
        RestHighLevelClient client = elasticClient.getRestHighLevelClient();
        //Json字符串作为数据源
        IndexRequest indexRequest1 = new IndexRequest(
                "test"
                //  "book",
                //        "3"
        );
        String jsonString = "{" +
                "\"name\":\"生命的诞生\"," +
                "\"type\":\"科学\"," +
                "\"price\":170.10 ," +
                "\"tags\":[1,2] ," +
                "\"remark7\":1" +
                "}";


        // jsonString = "{'name':'name','tyep':'科学','tags':['学校','工厂']}";


        indexRequest1.source(jsonString, XContentType.JSON);
        indexRequest1.id("2");

        try {

            client.index(indexRequest1, RequestOptions.DEFAULT);

//            ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
//                @Override
//                public void onResponse(IndexResponse indexResponse) {
//                    log.info("indexResponse succeed!");
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    log.info("indexResponse failed!");
//                }
//            };
//
//            jsonString = "{" +
//                    "\"name\":\"生命的诞生\"," +
//                    "\"type\":\"科学\"," +
//                    "\"price\":170.11" +
//                    "}";
//
//            indexRequest1.source(jsonString, XContentType.JSON);
//            indexRequest1.id("2");
//
//            client.indexAsync(indexRequest1, RequestOptions.DEFAULT, listener);


            //    client.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/add4")
    public void add4() {
        RestHighLevelClient client = elasticClient.getRestHighLevelClient();
        //Map集合作为数据源
        Map jsonMap = new HashMap<>();
        jsonMap.put("user", "userByMap");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "messageByMap");


        IndexRequest indexRequest2 = new IndexRequest("posts")
                .source(jsonMap);

        try {
            client.index(indexRequest2, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        RestHighLevelClient client = elasticClient.getRestHighLevelClient();


        //XContentBuilder作为数据源
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.field("user", "userXXX");
            builder.timeField("postDate", new Date());
            builder.field("message", "messageXXX");
            builder.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexRequest indexRequest3 = new IndexRequest("posts", "doc", "1")
                .source(builder);
        try {

            client.index(indexRequest3, RequestOptions.DEFAULT);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
