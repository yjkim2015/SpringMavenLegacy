package com.legacy.controller.exception;

public class AbstractTException extends RuntimeException {

    public AbstractTException() {
    }

    public AbstractTException(String message) {
        super(message);
    }

    public AbstractTException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractTException(Throwable cause) {
        super(cause);
    }

    public AbstractTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
