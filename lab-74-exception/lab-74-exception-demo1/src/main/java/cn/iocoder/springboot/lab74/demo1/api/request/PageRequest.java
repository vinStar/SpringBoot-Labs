package cn.iocoder.springboot.lab74.demo1.api.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 王守钰
 * @description 分页请求基类
 * @date 2020/8/5 8:50
 */
@Data
@ToString
@EqualsAndHashCode
//@ApiModel("请求基类")
public abstract class PageRequest extends BaseRequest {

    /**
     * 当前页码
     */
//    @ApiModelProperty("当前页码")
    private long pageNum = 1;

    /**
     * 分页条数
     */
//    @ApiModelProperty("分页条数-默认是10条一页")
    private long pageSize = 10;

}
