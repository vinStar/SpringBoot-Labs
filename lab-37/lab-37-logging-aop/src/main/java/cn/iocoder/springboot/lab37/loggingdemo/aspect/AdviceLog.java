package cn.iocoder.springboot.lab37.loggingdemo.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
@Order(1)
public class AdviceLog {
    private static Logger log = LoggerFactory.getLogger(AdviceLog.class);

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
            map = com.example.logging.AdviceLogUtil.getRawData(joinPoint, requestData);
        }
        // heard信息
        map.put("【httpHeader】", com.example.logging.AdviceLogUtil.getHeadersMap(request));
        // parameters信息
        map.put("【httpParams】", com.example.logging.AdviceLogUtil.getParametersMap(isLogGather ? joinPoint : null, request));
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
