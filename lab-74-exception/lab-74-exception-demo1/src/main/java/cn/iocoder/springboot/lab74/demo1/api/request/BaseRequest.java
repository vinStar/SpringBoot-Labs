package cn.iocoder.springboot.lab74.demo1.api.request;

import lombok.Data;

import java.io.Serializable;


//@ApiModel("请求基类")
@Data
public abstract class BaseRequest implements Serializable {
//    @ApiModelProperty("请求UUID，要求每次请求生成")
    private String uuid;
    /**
     * 系统编码
     */
//    @ApiModelProperty(value = "系统编码",allowableValues = "customer,vip",required = true)
    private String systemCode;
}
