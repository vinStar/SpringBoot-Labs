package cn.iocoder.springboot.lab74.demo1.api.exception;


public class OrderCenterException extends MumwayException {

    private MumwayErrorCode errorCode;


    public OrderCenterException(MumwayErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public OrderCenterException(String message, MumwayErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public OrderCenterException(String message, Throwable cause, MumwayErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public OrderCenterException(Throwable cause, MumwayErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public OrderCenterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, MumwayErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public MumwayErrorCode getErrorCode() {
        return errorCode;
    }

    public OrderCenterException(String message) {
        super(message);
    }

    public OrderCenterException(String message, Integer code) {
        super(message,code);
    }
}
