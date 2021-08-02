package cn.iocoder.springboot.lab39.skywalkingdemo.controller;

import cn.iocoder.springboot.lab39.skywalkingdemo.dataobject.ESUserDO;
import cn.iocoder.springboot.lab39.skywalkingdemo.repository.ESUserRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private ESUserRepository userRepository;

    @GetMapping("/elasticsearch")
    public String mysql() {

        return this.findById(1).toString();
        //return "elasticsearch";
    }

    @GetMapping("/get1")
    public Page<ESUserDO> get1() {
        ESUserDO esUserDO = ESUserDO.builder().id(1)
                .username("tom")
                .createTime(new Date())
                .remark("我爱你中国")
                .build();

        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 1;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 100;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        String[] strArray = new String[1];
        strArray[0] = "我";
        return userRepository.searchSimilar(esUserDO, strArray, pageable);
        //return "elasticsearch";
    }

    @GetMapping("/get2")
    public Page<ESUserDO> get2() {


        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 1;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 100;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };



        QueryBuilder queryBuilder = QueryBuilders.boolQuery().
                must(QueryBuilders.queryStringQuery("我"));
              //  must(QueryBuilders.matchQuery("goodsStatus",1));

        return userRepository.search(queryBuilder,pageable);
        //return "elasticsearch";
    }

    @GetMapping("/add")
    public String add() {
        ESUserDO esUserDO = ESUserDO.builder().id(1)
                .username("tom")
                .createTime(new Date())
                .remark("我爱你中国")
                .build();
        return userRepository.save(esUserDO).toString();

    }

    public ESUserDO findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

}
