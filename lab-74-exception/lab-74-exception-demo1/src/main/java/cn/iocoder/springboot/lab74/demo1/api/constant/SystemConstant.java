package cn.iocoder.springboot.lab74.demo1.api.constant;


public interface SystemConstant {

    /**
     * 正确
     */
    Integer SUCCESS = 0;

    /**
     * 系统错误
     */
    Integer SYSTEM_ERROR = -1;

    /**
     * 参数错误
     */
    Integer PARAM_ERROR = 10001;

    /**
     * 为空错误
     */
    Integer STR_ISNULL_ERROR = 20000;

    /**
     * 正确消息
     */
    String SUCCESS_MESSAGE = "OK";

    /**
     * 系统错误消息
     */
    String SYSTEM_ERROR_MESSAGE = "系统错误";

    /**
     * 参数错误信息
     */
    String PARAM_ERROR_MESSAGE = "参数 {0} 为空";

    /**
     * 为空错误信息
     */
    String STR_ISNULL_ERROR_MESSAGE = "字符串参数 {0} 为空！";


    /**
     * 请求头token 变量
     */
    String X_AUTH_USER_TOKEN = "X-AUTH-USERTOKEN";

    /**
     * 请求头APP_Id 变量
     */
    String X_AUTH_APP_ID = "X-AUTH-APPID";

    /**
     * 请求头X-AUTH-TIMESTAMP 变量 时间戳
     */
    String X_AUTH_TIMESTAMP = "X-AUTH-TIMESTAMP";


    /**
     * 新用户系统请求头token变量
     */
    String X_AUTH_UTOKEN = "X-Auth-Utoken";

    /**
     * 新用户系统请求头Appid变量
     */
    String X_AUTH_APPID = "X-Auth-Appid";

    /**
     * 新用户系统请求头时间戳变量
     */
    String X_AUTH_TS = "X-Auth-Ts";

    /**
     * 新用户系统请求头请求的一次性参数
     */
    String X_AUTH_NONCESTR = "X-Auth-Noncestr";

    /**
     * 新用户系统请求头签名计算的值
     */
    String X_AUTH_SIGN = "X-Auth-Sign";

}
