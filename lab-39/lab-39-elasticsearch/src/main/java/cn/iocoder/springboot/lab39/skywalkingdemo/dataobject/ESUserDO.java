package cn.iocoder.springboot.lab39.skywalkingdemo.dataobject;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "user", // 索引名
//        type = "user", // 类型。未来的版本即将废弃
        shards = 5, // 默认索引分区数
        replicas = 1, // 每个分区的备份数
        refreshInterval = "1s" // 刷新间隔
)
@Data
@Builder
@ToString
public class ESUserDO {

    @Id
    private Integer id;
    /**
     * 账号
     */
    @Field(type = FieldType.Keyword)
    private String username;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String remark;
    /**
     * 创建时间
     */
    @Field(index = false, type = FieldType.Keyword)
    private Date createTime;


}
