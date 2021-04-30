package cn.iocoder.springboot.lab74.demo1.api.response;


import cn.iocoder.springboot.lab74.demo1.api.constant.SystemConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;


/**
 * 响应信息
 */
@Data
@Builder
@ToString
@ApiModel("响应数据")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码", required = true)
    private Integer errcode;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码", required = true)
    private Integer code;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息", required = true)
    private String errmsg;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息", required = true)
    private String msg;

    /**
     * 响应数据 json格式
     */
    @ApiModelProperty("响应数据")
    private T data;

    /**
     * 返回mdcid
     */
    @ApiModelProperty(value = "请求时的uuid")
    private String uuid;

    /**
     * 失败
     *
     * @param code 失败code
     * @param msg  失败消息
     * @param uuid 请求id
     * @param data 返回数据
     * @return 构造对象
     */
    public static BaseResponse fail(Integer code, String msg, String uuid, Object data) {
        return BaseResponse.builder()
                .errcode(code)
                .code(code)
                .errmsg(msg)
                .msg(msg)
                .uuid(uuid)
                .data(data)
                .build();
    }

    /**
     * 失败
     *
     * @param code 失败code
     * @param msg  失败消息
     * @param uuid 请求id
     * @return 构造对象
     */
    public static BaseResponse fail(Integer code, String msg, String uuid) {
        return BaseResponse.builder()
                .errcode(code)
                .code(code)
                .errmsg(msg)
                .msg(msg)
                .uuid(uuid)
                .build();
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> result = new BaseResponse<>();
        result.code = 1;
        result.data = data;

        return result;
    }

    /**
     * 成功
     *
     * @param uuid 请求id
     * @param data 成功数据
     * @return 包装结果
     */
    public static <T> BaseResponse<T> success(String uuid, T data) {
        return (BaseResponse<T>) BaseResponse.builder()
                .errcode(SystemConstant.SUCCESS)
                .code(SystemConstant.SUCCESS)
                .errmsg(SystemConstant.SUCCESS_MESSAGE)
                .msg(SystemConstant.SUCCESS_MESSAGE)
                .data(data)
                .uuid(uuid)
                .build();
    }
}
