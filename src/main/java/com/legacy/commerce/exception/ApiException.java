package com.legacy.commerce.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends AbstractTException {

    /** The exception code. */
    String exceptionCode;

    /** The exception info. */
    Object exceptionInfo;

    boolean logable = true;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7100262123084299295L;

    public ApiException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        setHttpStatus(httpStatus);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param httpStatus
     *            the http status
     * @param exceptionCode
     *            the exception code
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(String message, HttpStatus httpStatus, String exceptionCode, Object exceptionInfo) {
        super(message);
        setHttpStatus(httpStatus);
        setExceptionCode(exceptionCode);
        setExceptionInfo(exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param exceptionCode
     *            the exception code
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(String message, String exceptionCode, Object exceptionInfo) {
        this(message, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param status
     *            the status
     */
    public ApiException(String message, HttpStatus status) {
        this(message, status, null, null);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(String message, Object exceptionInfo) {
        this(message, HttpStatus.SERVICE_UNAVAILABLE, null, exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(Object exceptionInfo) {
        setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
        setExceptionInfo(exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param httpStatus
     *            the http status
     * @param exceptionCode
     *            the exception code
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(String message, Throwable cause, HttpStatus httpStatus, String exceptionCode, Object exceptionInfo) {
        super(message, cause);
        setHttpStatus(httpStatus);
        setExceptionCode(exceptionCode);
        setExceptionInfo(exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param exceptionCode
     *            the exception code
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(String message, Throwable cause, String exceptionCode, Object exceptionInfo) {
        this(message, cause, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, exceptionInfo);

    }

    public ApiException(String message, Throwable cause, String exceptionCode, Object exceptionInfo, boolean logable) {
        this(message, cause, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, exceptionInfo);
        setLoggable(false);

    }

    public ApiException(String message, String exceptionCode, boolean logable) {
        this(message, null, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, null);
        setLoggable(false);

    }

    public ApiException(String message, String exceptionCode, Object exceptionInfo, boolean logable) {
        this(message, null, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, exceptionInfo);
        setLoggable(false);

    }

    public ApiException(String exceptionCode, Object exceptionInfo, boolean logable) {
        this(null, null, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, exceptionInfo);
        setLoggable(false);

    }

    public ApiException(String exceptionCode, boolean logable) {
        this(null, null, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, null);
        setLoggable(false);

    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(String message, Throwable cause, Object exceptionInfo) {
        this(message, cause, HttpStatus.SERVICE_UNAVAILABLE, null, exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param cause
     *            the cause
     * @param httpStatus
     *            the http status
     * @param exceptionCode
     *            the exception code
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(Throwable cause, HttpStatus httpStatus, String exceptionCode, Object exceptionInfo) {
        this(null, cause, httpStatus, exceptionCode, exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param cause
     *            the cause
     * @param exceptionCode
     *            the exception code
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(Throwable cause, String exceptionCode, Object exceptionInfo) {
        this(null, cause, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, exceptionInfo);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param cause
     *            the cause
     * @param exceptionInfo
     *            the exception info
     */
    public ApiException(Throwable cause, Object exceptionInfo) {
        this(null, cause, HttpStatus.SERVICE_UNAVAILABLE, null, exceptionInfo);

    }

    public ApiException(Throwable cause, boolean loggable) {
        this(null, cause, HttpStatus.SERVICE_UNAVAILABLE, null, null);
        setLoggable(loggable);

    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param httpStatus
     *            the http status
     * @param exceptionCode
     *            the exception code
     */
    public ApiException(String message, HttpStatus httpStatus, String exceptionCode) {
        this(message, httpStatus, exceptionCode, null);
    }

    public ApiException(String message, HttpStatus httpStatus, String exceptionCode, boolean isLogable) {
        this(message, httpStatus, exceptionCode, null);
        this.logable = isLogable;
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param exceptionCode
     *            the exception code
     */
    public ApiException(String message, String exceptionCode) {
        this(message, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, null);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     */
    public ApiException(String message) {
        this(message, HttpStatus.SERVICE_UNAVAILABLE, null, null);
    }

    /**
     * Instantiates a new api exception.
     */
    public ApiException() {
        setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param httpStatus
     *            the http status
     * @param exceptionCode
     *            the exception code
     */
    public ApiException(String message, Throwable cause, HttpStatus httpStatus, String exceptionCode) {
        this(message, cause, httpStatus, exceptionCode, null);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param exceptionCode
     *            the exception code
     */
    public ApiException(String message, Throwable cause, String exceptionCode) {
        this(message, cause, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode, null);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ApiException(String message, Throwable cause) {
        this(message, cause, HttpStatus.SERVICE_UNAVAILABLE, null, null);

    }

    /**
     * Instantiates a new api exception.
     *
     * @param cause
     *            the cause
     * @param httpStatus
     *            the http status
     * @param exceptionCode
     *            the exception code
     */
    public ApiException(Throwable cause, HttpStatus httpStatus, String exceptionCode) {
        this(cause, httpStatus, exceptionCode, null);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param cause
     *            the cause
     * @param exceptionCode
     *            the exception code
     */
    public ApiException(Throwable cause, String exceptionCode) {
        this(cause, HttpStatus.SERVICE_UNAVAILABLE, exceptionCode);
    }

    /**
     * Instantiates a new api exception.
     *
     * @param cause
     *            the cause
     */
    public ApiException(Throwable cause) {
        super(cause);
        setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /** The http status. */
    private HttpStatus httpStatus;

    /**
     * Gets the http status.
     *
     * @return the http status
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Sets the http status.
     *
     * @param httpStatus
     *            the new http status
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * Gets the exception code.
     *
     * @return the exception code
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * Sets the exception code.
     *
     * @param exceptionCode
     *            the new exception code
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * Gets the exception info.
     *
     * @return the exception info
     */
    public Object getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * Sets the exception info.
     *
     * @param exceptionInfo
     *            the new exception info
     */
    public void setExceptionInfo(Object exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    @Deprecated
    public boolean isLogable() {
        return logable;
    }

    @Deprecated
    public void setLogable(boolean logable) {
        this.logable = logable;
    }

    public boolean isLoggable() {
        return logable;
    }

    public void setLoggable(boolean logable) {
        this.logable = logable;
    }

    public static ApiException makeWithUnloggableException(Throwable e) {
        ApiException ex = new ApiException(e);
        ex.setLoggable(false);
        return ex;
    }
}

