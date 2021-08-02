package cn.iocoder.springboot.lab39.skywalkingdemo.controller;

import cn.iocoder.springboot.lab39.skywalkingdemo.dataobject.ESUserDO;
import cn.iocoder.springboot.lab39.skywalkingdemo.dataobject.WorkerDo;
import cn.iocoder.springboot.lab39.skywalkingdemo.repository.ESUserRepository;
import com.alibaba.fastjson.JSON;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private JestClient jestClient;

    @Autowired
    private ESUserRepository userRepository;

    @GetMapping("/get1")
    public String mysql() {
        this.save(1);
        return this.findById(1).toString();
    }

    @GetMapping("/create")
    public String create() {
        //1. 给ES中索引(保存)一个文档
        ESUserDO user = ESUserDO.builder()
                .id(1)
                .username("username")
                .remark("我爱你中国")
                .createTime(new Date()).build();

        //2. 构建一个索引
        Index index = new Index.Builder(user).index("user").type("_doc").build();
        try {
            //3. 执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "succ";
    }


    @GetMapping("/add")
    public String add() {
        /*
         * ElasticSerach使用JSON作为文档的序列化格式
         * new Index.Build(source) source:可以是JSON串、Map<Key,Value>、Pojo
         */
        ESUserDO user = ESUserDO.builder()
                .id(2)
                .username("username")
                .remark("我爱你中国")
                .createTime(new Date()).build();
        //索引数据
        Index index = new Index.Builder(user).
                index("user").
                type("_doc").
                //  id("1").//手动指定文档ID
                        build();


        try {
            jestClient.execute(index);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return "succ";
    }

    @GetMapping("/add2")
    public String add2() {
        /*
         * ElasticSerach使用JSON作为文档的序列化格式
         * new Index.Build(source) source:可以是JSON串、Map<Key,Value>、Pojo
         */
        ESUserDO user = ESUserDO.builder()
                .id(2)
                .username("username")
                .remark("我爱你中国")
                .field1("我喜欢你")
                //  .item1("a")
                .createTime(new Date()).build();
        //索引数据
        Index index = new Index.Builder(user).
                index("user").
                type("_doc").
                //  id("1").//手动指定文档ID
                        build();


        try {
            jestClient.execute(index);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return "succ";
    }

    @GetMapping("/add3")
    public String add3() {
        /*
         * ElasticSerach使用JSON作为文档的序列化格式
         * new Index.Build(source) source:可以是JSON串、Map<Key,Value>、Pojo
         */
        ESUserDO user = ESUserDO.builder()
                .id(2)
                .username("username")
                .remark("我爱你中国")
                .field1("我喜欢你")
                .item1("a")
                .createTime(new Date()).build();
        //索引数据
        Index index = new Index.Builder(user).
                index("user").
                type("_doc").
                //  id("1").//手动指定文档ID
                        build();


        try {
            jestClient.execute(index);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return "succ";
    }


    @GetMapping("/get2")
    public String get2() {

        //查询所有
        String queryJson = "{\n" +
                "   \"query\" : {\n" +
                "      \"match_all\" : {}\n" +
                "   }\n" +
                "}\n";
        //使用JSON串来查询
        Search search = new Search.Builder(queryJson)
                //可以添加多个index和type
                .addIndex("user")
                //.addType("book")
                .build();
        try {
            SearchResult result = jestClient.execute(search);
            List<ESUserDO> list = result.getSourceAsObjectList(ESUserDO.class, false);
            return JSON.toJSONString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "null";

    }

    @GetMapping("/get3")
    public String get3() {

        //查询所有
        String queryJson = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"filter\": [\n" +
                "        { \"term\": {\"id\":1}\n" +
                "          },\n" +
                "        {\"term\": {\"username\": \"username\"}\n" +
                "          }\n" +
                "        ]\n" +
                "        \n" +
                "      , \"must\": [\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"remark\": \"我\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        //使用JSON串来查询
        Search search = new Search.Builder(queryJson)
                //可以添加多个index和type
                .addIndex("user")
                //.addType("book")
                .build();
        try {
            SearchResult result = jestClient.execute(search);
            List<ESUserDO> list = result.getSourceAsObjectList(ESUserDO.class, false);
            return JSON.toJSONString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "null";

    }

    public void save(Integer id) {
        ESUserDO user = ESUserDO.builder()
                .id(id)
                .username("username")
                .remark("我爱你中国")
                .createTime(new Date()).build();

        userRepository.save(user);
    }

    public ESUserDO findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }


    @GetMapping("/add4")
    public String add4() {

        GeoPoint geoPoint = new GeoPoint(11.11, 22.22);
        /*
         * ElasticSerach使用JSON作为文档的序列化格式
         * new Index.Build(source) source:可以是JSON串、Map<Key,Value>、Pojo
         */
        WorkerDo worker = WorkerDo.builder()
                .id(3)
                .username("username")
                .remark("我爱你中国")
                .arr(new Object[]{"A", "B", "C"})
                .t1(100)
                .t2(100)
                .t3(100)
                .t4(100)
                .t5(100)
                .t6(100)
                .t7(100)
                .t8(100)
                .t9(100)
                .t10(100)
                .field1("苏州的招生老师在吗")
                .field11("苏州的招生老师在吗")
                .field12("苏州的招生老师在吗")
                .field13("苏州的招生老师在吗")
                .field14("苏州的招生老师在吗")
                .field15("苏州的招生老师在吗")
                .field16("苏州的招生老师在吗")
                .field17("苏州的招生老师在吗")
                .field18("苏州的招生老师在吗")
                .field19("苏州的招生老师在吗")
                .field2(geoPoint)
                .field41("苏州的招生老师在吗")
                .field42("苏州的招生老师在吗")
                .field43("苏州的招生老师在吗")
                .field44("苏州的招生老师在吗")
                .field45("苏州的招生老师在吗")
                .field46("苏州的招生老师在吗")
                .field47("苏州的招生老师在吗")
                .field48("苏州的招生老师在吗")
                .field51("苏州的招生老师在吗")
                .field52("苏州的招生老师在吗")
                .field53("苏州的招生老师在吗")
                .field54("苏州的招生老师在吗")
                .field55("苏州的招生老师在吗")
                .field56("苏州的招生老师在吗")
                .field57("苏州的招生老师在吗")
                .field58("苏州的招生老师在吗")
                .field59("苏州的招生老师在吗")
                .field61("苏州的招生老师在吗")
                .field62("苏州的招生老师在吗")
                .field63("苏州的招生老师在吗")
                .field64("苏州的招生老师在吗")
                .field65("苏州的招生老师在吗")
                .field66("苏州的招生老师在吗")
                .field67("苏州的招生老师在吗")
                .field68("苏州的招生老师在吗")
                .field69("苏州的招生老师在吗")
                .field71("苏州的招生老师在吗")
                .field72("苏州的招生老师在吗")
                .field73("苏州的招生老师在吗")
                .field81("苏州的招生老师在吗")
                .field82("苏州的招生老师在吗")
                .field83("苏州的招生老师在吗")
                .field84("苏州的招生老师在吗")
                .field85("苏州的招生老师在吗")
                .field86("苏州的招生老师在吗")
                .field87("苏州的招生老师在吗")
                .field88("苏州的招生老师在吗")
                .field89("苏州的招生老师在吗")
                .field21("苏州的招生老师在吗")
                .field22("苏州的招生老师在吗")
                .field23("苏州的招生老师在吗")
                .field24("苏州的招生老师在吗")
                .field25("苏州的招生老师在吗")
                .field26("苏州的招生老师在吗")
                .field27("苏州的招生老师在吗")
                .field28("苏州的招生老师在吗")
                .field29("苏州的招生老师在吗")
                //.field3(new String()[]{"aa","bb"})

                .build();
        //索引数据
        Index index = new Index.Builder(worker).
                index("worker").
                type("_doc").
                //  id("1").//手动指定文档ID
                        build();


        try {
            jestClient.execute(index);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return "succ";
    }
}
