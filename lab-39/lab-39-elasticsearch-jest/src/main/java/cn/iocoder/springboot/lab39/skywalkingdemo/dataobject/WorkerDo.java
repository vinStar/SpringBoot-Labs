package cn.iocoder.springboot.lab39.skywalkingdemo.dataobject;

import io.searchbox.annotations.JestId;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "worker", // 索引名
        //type = "user", // 类型。未来的版本即将废弃
        shards = 5, // 默认索引分区数
        replicas = 1, // 每个分区的备份数
        refreshInterval = "1s" // 刷新间隔
)
@Data
@Builder
@ToString
public class WorkerDo {


    @JestId
    private Integer id;

    @Field(type = FieldType.Integer)
    private Integer t1;

    @Field(type = FieldType.Integer)
    private Integer t2;
    @Field(type = FieldType.Integer)
    private Integer t3;
    @Field(type = FieldType.Integer)
    private Integer t4;
    @Field(type = FieldType.Integer)
    private Integer t5;
    @Field(type = FieldType.Integer)
    private Integer t6;
    @Field(type = FieldType.Integer)
    private Integer t7;
    @Field(type = FieldType.Integer)
    private Integer t8;
    @Field(type = FieldType.Integer)
    private Integer t9;
    @Field(type = FieldType.Integer)
    private Integer t10;


    /**
     * 账号
     */
    @Field(type = FieldType.Text)

    private String username;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String remark;


    @GeoPointField
    private GeoPoint field2;

    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field1;

    /**
     * 备注
     */
    @Field(type = FieldType.Auto)
    private String field3;


    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field11;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field12;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field13;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field14;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field15;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field16;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field17;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field18;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field19;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field21;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field22;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field23;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field24;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field25;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field26;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field27;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field28;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field29;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field4;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field51;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field52;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field53;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field54;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field55;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field56;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field57;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field58;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field59;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field61;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field62;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field63;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field64;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field65;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field66;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field67;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field68;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field69;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field71;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field72;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field73;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field48;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field47;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field46;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field45;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field44;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field43;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field42;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field41;


    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field81;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field82;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field83;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field84;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field85;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field86;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field87;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field88;
    /**
     * 备注
     */
    @Field(type = FieldType.Text)
    private String field89;


    private Object[] arr;


}
