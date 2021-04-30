package cn.iocoder.springboot.lab74.demo1.config;


import cn.iocoder.springboot.lab74.demo1.api.exception.MumwayException;
import cn.iocoder.springboot.lab74.demo1.api.exception.OrderCenterException;
import cn.iocoder.springboot.lab74.demo1.api.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.springboot.lab74.demo1.api.constant.SystemConstant.*;


@Slf4j
@RestController
@ControllerAdvice
public class ExceptionConfig {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private MessageSource messageSource;

    /**
     * 缺少参数异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public BaseResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        StringBuffer sb = new StringBuffer();
        sb.append("类型：【").append(exception.getParameterType())
                .append("】，参数：【")
                .append(exception.getParameterName())
                .append("】必传");
        log.error("{}", sb.toString());
        return BaseResponse.fail(PARAM_ERROR, sb.toString(), "fail");
    }

    /**
     * MethodArgumentNotValidException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuffer sb = new StringBuffer();
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append("【")
                    .append(fieldError.getField())
                    .append(":")
                    .append(fieldError.getDefaultMessage())
                    .append("】");
        }
        log.error("{}", sb.toString());
        return BaseResponse.fail(PARAM_ERROR, sb.toString(), "fail");
    }

    /**
     * 参数错误信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse bindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error("{}", message);
        return BaseResponse.fail(PARAM_ERROR, message, "fail");
    }

    /**
     * 参数校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    public BaseResponse validateException(ValidationException e) {

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            log.error("{}", message);
            return BaseResponse.builder().errcode(PARAM_ERROR).errmsg(message).uuid("fail").build();
        }
        return BaseResponse.fail(PARAM_ERROR, "参数错误", "fail");
    }

    /**
     * 请求方法不支持异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public BaseResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest httpServletRequest) {
        log.error("请求方法不支持!", e);
        return BaseResponse.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法不支持，请检查请求的url或者请求方式是否正确", "fail");
    }


    /**
     * 异常请求
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public BaseResponse baseException(Exception e) {
        log.error("请求异常！{}", ExceptionUtils.getStackTrace(e));
        log.error("请求异常2！{}", ExceptionUtils.getMessage(e));


        return BaseResponse.fail(SYSTEM_ERROR, SYSTEM_ERROR_MESSAGE + getRootException(e), "fail");
    }


    public static String getRootException(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        return throwable.toString();
    }

    /**
     * 订单全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = OrderCenterException.class)
    public BaseResponse orderCenterExceptionHandler(OrderCenterException e) {
        String message;
        try {
            message = messageSource.getMessage(e.getErrorCode().errorCode().toString(), null, LocaleContextHolder.getLocale());
        } catch (Exception ex) {
            message = e.getMessage();
        }
        return BaseResponse.fail(e.getErrorCode().errorCode(), message, "fail");
    }

    /**
     * 好孕妈妈全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MumwayException.class)
    public BaseResponse mumwayExceptionHandler(MumwayException e) {
        String message;
        try {
            message = messageSource.getMessage(e.getCode().toString(), null, LocaleContextHolder.getLocale());
        } catch (Exception ex) {
            message = e.getMessage();
        }
        return BaseResponse.fail(e.getCode(), message, "fail");
    }




    @GetMapping(value = {"/error"})
    public BaseResponse exception(HttpServletRequest request, HttpServletResponse response) {
        Integer errorCode = response.getStatus();
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> map = errorAttributes.getErrorAttributes(webRequest, true);
        log.error("错误码：{}，错误信息：{}", errorCode, map);
        return BaseResponse.fail(errorCode, SYSTEM_ERROR_MESSAGE, "fail");
    }


}
