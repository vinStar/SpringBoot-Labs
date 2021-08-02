package cn.iocoder.springboot.lab39.skywalkingdemo.es.utils;

import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.ClearScroll;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 〈一句话功能简述〉操作ES的Jest客户端<br>
 * 〈功能详细描述〉
 *
 * @author wangzha
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class JestService<T> implements InitializingBean {

    public static Logger logger = LoggerFactory.getLogger(JestService.class);

    /**
     * jest客户端,单例
     */
    private static JestClient client = null;

//    @Value("${es.cluster}")
//    private String cluster;

    /**
     * es 读取超时时间
     */
    private static final String ES_READ_TIMEOUT = "es.read.timeout";

    /**
     * es 连接超时时间
     */
    private static final String ES_CONNECT_TIMEOUT = "es.connect.timeout";

    /**
     * 滚动ID key
     */
    private static final String SCORLL_ID_KEY = "_scroll_id";

    /**
     * 命中集 key
     */
    private static final String QUERY_HITS_KEY = "hits";

    /**
     * 数据源 key
     */
    private static final String SOURCE_KEY = "_source";

    /**
     * 搜索上下文的时间,用来支持该批次
     */
    private static final String SCROLL_ALIVE_TIME = "5m";

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 客户端初始化
     */
    public void init() {
        logger.info("ESJestClient Init Start...");
        synchronized (JestService.class) {
            if (client != null) {
                return;
            }
//            String[] uriArr = cluster.split(Symbols.COMMA);
            String[] uriArr = new String[]{"http://localhost:9200",
            };
            if (uriArr.length > 0) {
                Set<String> serverUris = new LinkedHashSet<String>();
                for (int i = 0; i < uriArr.length; i++) {
                    if (!StringUtils.isEmpty(uriArr[i]))
                        serverUris.add(uriArr[i]);
                }
                JestClientFactory factory = new JestClientFactory();
                factory.setHttpClientConfig(new HttpClientConfig.Builder(serverUris)
//                        .connTimeout(SCMServiceCfg.getNodeTransInt(ES_CONNECT_TIMEOUT))
//                        .readTimeout(SCMServiceCfg.getNodeTransInt(ES_READ_TIMEOUT))
                        .connTimeout(1000)
                        .readTimeout(10000)
                        .multiThreaded(true)
                        .build());
                client = factory.getObject();
                logger.info(String.format("ESJestClient init success,ip:[%s]", uriArr));
            } else {
                //   throw new AppException("ESJestClient init error,uri is null,Check scm ! ", ResponseCode.FAIL.getCode());
            }
        }
    }

    /**
     * 资源释放
     */
    public void destory() {
        if (client != null) {
            client.shutdownClient();
            logger.debug("ESJestClient destory ! ");
        }
    }


    /**
     * 获取指定索引信息
     *
     * @param indexName
     * @param typeName
     * @return
     */
    public String getMapping(String indexName, String typeName) {
        GetMapping.Builder builder = new GetMapping.Builder();
        builder.addIndex(indexName).addType(typeName);
        String res = null;
        try {
            JestResult result = client.execute(builder.build());
            if (result != null && result.isSucceeded()) {
                res = result.getSourceAsObject(JsonObject.class).toString();
            }
        } catch (Exception e) {
            logger.error("es get mapping Exception ", e);
            // throw new AppException("获取索引信息失败", ResponseCode.FAIL.getCode());
        }
        return res;
    }

    /**
     * 插入单条数据
     * 若该条数据已经存在,则覆盖。
     *
     * @param t
     * @return
     */
    public boolean insertOrUpdateDoc(T t, String uniqueId, String index, String type) {
        //是否插入成功标识
        boolean flag = false;
        Index.Builder builder = new Index.Builder(t);
        builder.id(uniqueId);
        builder.refresh(true);
        Index indexDoc = builder.index(index).type(type).build();
        JestResult result;
        try {
            result = client.execute(indexDoc);
            if (result != null && result.isSucceeded()) {
                flag = true;
            }
        } catch (IOException e) {
            logger.error("ESJestClient insertDoc exception", e);
        }
        return flag;
    }

    /**
     * 批量插入数据
     *
     * @param list
     * @param index
     * @param type
     * @return
     */
//    @SuppressWarnings("unchecked")
//    public BatchOperaResult batchInsertDoc(List<T> list, String index, String type) {
//        //批量数据操作结果
//        BatchOperaResult batchOperaResult = null;
//        try {
//            Bulk.Builder bulkBuilder = new Bulk.Builder();
//            //循环构造批量数据
//            for (T t : list) {
//                Index indexDoc = new Index.Builder(t).index(index).type(type).build();
//                bulkBuilder.addAction(indexDoc);
//            }
//            BulkResult br = client.execute(bulkBuilder.build());
//            if (!br.isSucceeded()) {
//                batchOperaResult = new BatchOperaResult(false, list, br.getFailedItems());
//            } else {
//                batchOperaResult = new BatchOperaResult(true, list);
//            }
//        } catch (Exception e) {
//            logger.error("ESJestClient.batchInsertDoc-exception", e);
//        }
//        return batchOperaResult;
//    }

//    /**
//     * 组合查询文档+滚动分页
//     * 采用条件:数据量大,每页的size应该很大
//     *
//     * @param index
//     * @param type
//     * @param combinedQueryDto
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    public CombinedQueryResult combinedQueryByScroll(String index, String type, CombinedQueryDto combinedQueryDto) {
//        CombinedQueryResult res = null;
//        String scrollId = combinedQueryDto.getScrollId();
//        Map<String, Object> req = combinedQueryDto.getParams();
//        JestResult result = null;
//        try {
//            //首次查询或滚动时间超时,则重新查询
//            if (StringUtils.isEmpty(scrollId)) {
//                //清除滚动ID
//                clearScrollIds();
//                //循环构造查询条件
//                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//                for (Map.Entry entry : req.entrySet()) {
//                    //过滤器中,must表示查询出来的文档必须包含传入的值
//                    boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey().toString(), entry.getValue()));
//                }
//                searchSourceBuilder.query(boolQueryBuilder).size(combinedQueryDto.getSize());
//                //构造查询条件,设置索引及类型
//                Search search = new Search.Builder(searchSourceBuilder.toString())
//                        .addIndex(index).addType(type).setParameter(Parameters.SCROLL, SCROLL_ALIVE_TIME)
//                        .build();
//                //第一次检索,拍下快照
//                result = client.execute(search);
//            } else {
//                //只能向后滚动,不能向前滚动
//                for (int i = 0; i < combinedQueryDto.getScrollTime(); i++) {
//                    //直接滚动
//                    SearchScroll scroll = new SearchScroll.Builder(scrollId, SCROLL_ALIVE_TIME).build();
//                    result = client.execute(scroll);
//                    //第一次滚动时,判断scrollId是否过期,过期抛出异常
//                    if (i == 1) {
//                        JsonObject errMsg = result.getJsonObject().getAsJsonObject("error");
//                        if (errMsg != null) {
//                            throw new AppException(errMsg.getAsJsonArray("root_cause")
//                                    .getAsString(), ResponseCode.ERROR.getCode());
//                        }
//                    }
//                }
//            }
//            if (result != null && !result.isSucceeded()) {
//                throw new AppException("ESJestClient ScrollQuery Fail...", ResponseCode.FAIL.getCode());
//            }
//            //构造返回查询返回结果
//            res = buildResponse(result);
//        } catch (IOException e) {
//            logger.error("ESJestClient ScrollQuery IOException...", e);
//            throw new AppException("从ES查询DOC异常...", ResponseCode.ERROR.getCode());
//        } catch (Exception e) {
//            logger.error("ESJestClient ScrollQuery Exception...", e);
//            throw new AppException("从ES查询DOC异常...", ResponseCode.ERROR.getCode());
//        }
//        return res;
//    }

    /**
     * 清楚滚动ID
     */
    private void clearScrollIds() {
        ClearScroll clearScroll = new ClearScroll.Builder().build();
        try {
            client.execute(clearScroll);
        } catch (IOException e) {
            logger.error("ESJestClient Clean ScrollIds Exception...", e);
        }
    }

    /**
     * 结果集为空时,gson串事例:
     * <p>
     * {"took":0,"timed_out":false,"_shards":{"total":1,"successful":1,"failed":0},"hits":{"total":0,"max_score":null,"hits":[]}}
     *
     * @param result
     * @return
     */
//    @SuppressWarnings("unchecked")
//    private CombinedQueryResult buildResponse(JestResult result) {
//        CombinedQueryResult res = new CombinedQueryResult();
//        JsonObject jsonObject = result.getJsonObject();
//        JsonArray jsonElements = jsonObject.getAsJsonObject(QUERY_HITS_KEY).getAsJsonArray(QUERY_HITS_KEY);
//        List<T> list = new ArrayList<T>();
//        Gson gson = new Gson();
//        for (JsonElement jsonElement : jsonElements) {
//            list.add((T) gson.fromJson(jsonElement, Map.class).get(SOURCE_KEY));
//        }
//        String scrollId = jsonObject.getAsJsonPrimitive(SCORLL_ID_KEY).getAsString();
//        //不为空,才算文档查询成功
//        if (list.size() > 0) {
//            res.setResList(list);
//            res.setScrollId(scrollId);
//        } else
//            throw new AppException("ESJestClient ScrollQuery Fail...", ResponseCode.FAIL.getCode());
//        return res;
//    }

    /**
     * 组合查询+深入分页
     * 采用条件:数据量小,不会进行大翻页时
     *
     * @param index
     * @param type
     * @param combinedQueryDto
     * @return
     */
//    public CombinedQueryResult combinedQueryByFromSize(String index, String type, CombinedQueryDto combinedQueryDto) {
//        CombinedQueryResult res = null;
//        JestResult result;
//        Map<String, Object> req = combinedQueryDto.getParams();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        List<T> list;
//        try {
//            for (Map.Entry entry : req.entrySet()) {
//                boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey().toString(), entry.getValue()));
//            }
//            searchSourceBuilder.query(boolQueryBuilder).size(combinedQueryDto.getSize()).from(combinedQueryDto.getFrom());
//            Search search = new Search.Builder(searchSourceBuilder.toString())
//                    .addIndex(index).addType(type).setParameter(Parameters.SCROLL, SCROLL_ALIVE_TIME)
//                    .setSearchType(SearchType.SCAN).build();
//            result = client.execute(search);
//            if (result != null && !result.isSucceeded()) {
//                throw new AppException("ESJestClient FromSizeQuery Fail...", ResponseCode.FAIL.getCode());
//            }
//            list = null;
//            JsonArray jsonElements = result.getJsonObject().getAsJsonObject(QUERY_HITS_KEY).getAsJsonObject(QUERY_HITS_KEY).getAsJsonArray(SOURCE_KEY);
//            if (jsonElements != null) {
//                list = new Gson().fromJson(jsonElements, new TypeToken<List<T>>() {
//                }.getType());
//            }
//        } catch (IOException e) {
//            logger.error("ESJestClient FromSizeQuery Exception...", e);
//            throw new AppException("从ES查询DOC异常...", ResponseCode.ERROR.getCode());
//        } catch (Exception e) {
//            logger.error("ESJestClient FromSizeQuery Exception...", e);
//            throw new AppException("从ES查询DOC异常...", ResponseCode.ERROR.getCode());
//        }
//        res.setResList(list);
//        return res;
//    }

    /**
     * 创建索引
     *
     * @param index 索引名称
     */
    public boolean createIndex(String index) {
        boolean result = false;
        try {
            JestResult jestResult = client.execute(new CreateIndex.Builder(index).build());
            if (jestResult != null && jestResult.isSucceeded()) {
                result = true;
            }
        } catch (IOException e) {
            logger.error("EsJestClient-createIndex error", e);
            //  throw new AppException("索引创建失败");
        }
        return result;
    }

    /**
     * @param index
     * @param type
     * @param mappingStr Gson串  {"service_log": {"properties": {"id": {"type": "string"},"service": {"type": "string"},"app_code": {"type": "string"}}}}
     * @return
     */
    public boolean createIndexMapping(String index, String type, String mappingStr) {
        boolean result = false;
        PutMapping putMapping = new PutMapping.Builder(index, type, mappingStr).build();
        try {
            JestResult jestResult = client.execute(putMapping);
            if (jestResult != null && jestResult.isSucceeded()) {
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}

