package cn.iocoder.springboot.lab74.demo1.api.exception;


public class MumwayException extends RuntimeException{

    /**
     * 错误码
     */
    protected Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MumwayException() {
    }

    public MumwayException(String message) {
        super(message);
    }

    public MumwayException(String message, Throwable cause) {
        super(message, cause);
    }

    public MumwayException(Throwable cause) {
        super(cause);
    }

    public MumwayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MumwayException(Integer code) {
        this.code = code;
    }

    public MumwayException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MumwayException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public MumwayException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    public MumwayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
