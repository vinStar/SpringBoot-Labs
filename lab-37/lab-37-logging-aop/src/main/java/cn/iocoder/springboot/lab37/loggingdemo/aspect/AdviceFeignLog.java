package cn.iocoder.springboot.lab37.loggingdemo.aspect;

import com.alibaba.fastjson.JSON;
import feign.Request;
import feign.Response;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 日志增强： 请求日志 返回日志 全局异常
 */
@Aspect
@Component
@Order(2)
public class AdviceFeignLog {
    private static Logger log = LoggerFactory.getLogger(AdviceFeignLog.class);

    /**
     * 切点二：FeignClient接口，为了获取响应字符串
     */
    private final String pointcutFeign = "execution(* com.example.feign..*(..))";

    /**
     * Feign接口切点
     */
    @Pointcut(value = pointcutFeign)
    public void logFeign() {
    }

    @Before(value = "logFeign()")
    public void beforeFeign(JoinPoint joinPoint) {
        try {
//            msgLog = new MsgLog();
//            // 请求时间
//            msgLog.setReqTime(new Date());
//            // 请求方 本系统
//            msgLog.setReqFrom(SystemEnum.SYSTEM_SELF.getCode());
//            // 被请求方 外系统
//            msgLog.setReqTo(SystemEnum.SYSTEM_OTHER.getCode());
//            // 调用类
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

//            msgLog.setClassName(methodSignature.getDeclaringTypeName());
//            // 调用方法
//            msgLog.setClassFunction(methodSignature.getMethod().getName());
            // 请求数据
            String reqmsg = JSON.toJSONString(joinPoint.getArgs());
            log.info("before feign : {} method {}", reqmsg, methodSignature.getMethod().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 切点一：Feign中的ribbon.LoadBalancerFeignClient调用入口，为了获取请求Request

    private final String pointcutRibbon = "execution(* org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient.execute(..))";

    // Feign中的Ribbon切点
    @Pointcut(value = pointcutRibbon)
    public void logRibbon() {
    }

    @Before(value = "logRibbon()")
    public void beforeRibbon(JoinPoint joinPoint) {
        try {
            // 请求数据头
            Request request = (Request) joinPoint.getArgs()[0];
          //   org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient.execute
//            // 请求类型
//            msgLog.setReqType(request.method());
//            // 请求URL
//            msgLog.setReqUrl(request.url());
            // 请求模块
            String[] split = request.url().split("/");
//            if (split.length > 2) {
//                // 请求模块
//                msgLog.setModel(split[split.length - 2]);
//                // 请求方法
//                String[] methods = split[split.length - 1].split("[?]");
//                msgLog.setFunction(methods[0]);
//            }
//            // 请求数据头信息
//            msgLog.setReqHeader(JSON.toJSONString(request.headers()));
//            // 请求IP
//            msgLog.setReqIp("0.0.0.0");
            // 打印文件日志
            log.info("before Ribbon : {} ", split, JSON.toJSONString(request.headers()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Around("logRibbon()")
    public Object myAroundFeign(ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getArgs().length > 0) {
            Request request = (Request) joinPoint.getArgs()[0];
            log.info("REQUEST >>>>>>>>>>>>");
            log.info("URL = "+request.url());
        }

        Response response = (Response) joinPoint.proceed();


        log.info("HEADERS = "+response.headers().toString());
        return response;

    }


    /**
     * 请求日志
     *
     * @param requestData
     * @throws Throwable
     */
    @Before("@annotation(com.example.logging.LogGather) && args(requestData,..)")
    public void requestLog(JoinPoint joinPoint, Object requestData) throws Throwable {
        requestLog(joinPoint, requestData, true);
    }

    /**
     * 打印日志的具体方法
     *
     * @param joinPoint
     * @param requestData
     * @param isLogGather 标识被正确映射的合法请求
     */
    public void requestLog(JoinPoint joinPoint, Object requestData, boolean isLogGather) {
        // 需要打印的日志集合
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //requestBody信息
        Map<String, Object> map = new HashMap<>();
        if (isLogGather && requestData != null) {
            map = AdviceLogUtil.getRawData(joinPoint, requestData);
        }
        // heard信息
        map.put("【httpHeader】", AdviceLogUtil.getHeadersMap(request));
        // parameters信息
        map.put("【httpParams】", AdviceLogUtil.getParametersMap(isLogGather ? joinPoint : null, request));
        log.info(" apiRequestParams: " + JSON.toJSONString(map));
    }


    /**
     * 返回日志
     *
     * @param retVal
     * @throws Throwable
     */
    @AfterReturning(value = "@annotation(com.example.logging.LogGather)", returning = "retVal")
    public void responseLog(Object retVal) {
        log.info("apiResponseParams: " + JSON.toJSONString(retVal));
    }

    /**
     * 异常增强日志
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(logGather)")
    public Object adviceLog(ProceedingJoinPoint joinPoint, com.example.logging.LogGather logGather) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            //记录无法映射到requestMapping正确处理的请求日志
            requestLog(joinPoint, null, false);

            boolean writeLog = true;
            String[] excludeException = logGather.excludeException();
            if (excludeException.length > 0) {
                for (int i = 0; i < excludeException.length; i++) {
                    if (e.getClass().getName().equals(excludeException[i])) {
                        writeLog = false;
                        break;
                    }
                }
            }
            if (writeLog) {
                log.error("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter error: ", e);
            }

            throw e;
        }
    }
}
