package cn.iocoder.springboot.labs.high.rest.dataobject;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class ESUserDO {


    private Integer id;
    /**
     * 账号
     */

    private String username;
    /**
     * 备注

    private String remark;
    /**
     * 创建时间
     */

    private Date createTime;


}
