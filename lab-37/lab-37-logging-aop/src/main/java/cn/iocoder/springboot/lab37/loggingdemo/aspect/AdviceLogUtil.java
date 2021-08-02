package cn.iocoder.springboot.lab37.loggingdemo.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AdviceLogUtil {

    /**
     * 获取 请求的requestBody
     *
     * @param joinPoint
     * @param requestData
     * @return
     */
    public static Map<String, Object> getRawData(JoinPoint joinPoint, Object requestData) {
        //判断请求的参数里面是否有需要 进行混淆的项
        Map<String, Object> rawMap = new HashMap<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        com.example.logging.LogGather logGather = methodSignature.getMethod().getAnnotation(com.example.logging.LogGather.class);
        if (logGather != null) {
            // Tag信息
            Map<String, String> tagMap = new HashMap<>();
            tagMap.put("tag", logGather.tag());
            tagMap.put("description", logGather.description());
            rawMap.put("【requestTag】", tagMap);
        }
        //获取raw参数
        try {
            Map<String, Object> jsonMap = JSON.parseObject(JSON.toJSONString(requestData), Map.class);
            for (String next : jsonMap.keySet()) {
                if (StringUtils.isNotEmpty(next)) {
                    jsonMap.put(next, jsonMap.get(next).toString());
                }
            }
            requestData = jsonMap;
            rawMap.put("【httpBody(raw)】", requestData);
        } catch (Exception e) {
            // json解析错误不做处理

        }

        return rawMap;
    }

    /**
     * 获取 请求的headers
     *
     * @param request
     * @return
     */
    public static Map<String, String> getHeadersMap(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("URI", request.getRequestURI());
        headerMap.put("HTTP_METHOD", request.getMethod());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headerMap.put(name, request.getHeader(name));
        }
        return headerMap;
    }

    /**
     * 获取 请求的formData
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParametersMap(JoinPoint joinPoint, HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        if (joinPoint != null) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            com.example.logging.LogGather logGather = methodSignature.getMethod().getAnnotation(com.example.logging.LogGather.class);
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                paramsMap.put(name, request.getParameter(name));
            }
        } else {
            // 非法请求的参数
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                paramsMap.put(name, request.getParameter(name));
            }
        }
        return paramsMap;
    }
}
