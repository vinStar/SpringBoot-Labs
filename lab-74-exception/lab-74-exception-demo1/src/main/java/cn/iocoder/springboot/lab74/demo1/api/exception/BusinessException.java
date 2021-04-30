package cn.iocoder.springboot.lab74.demo1.api.exception;

import lombok.Data;

import java.text.MessageFormat;


@Data
public class BusinessException extends MumwayException {


    private BusinessException(){
    }

    public BusinessException(String message, Integer code, String... param) {

        super(MessageFormat.format(message,param));
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessException(Integer code) {
        super(code);
    }

    public BusinessException(String message, Integer code) {
        super(message, code);
    }

    public BusinessException(Throwable cause, Integer code) {
        super(cause, code);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
