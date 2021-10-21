package com.legacy.commerce.logging;

import com.legacy.commerce.common.JsonUtils;
import com.legacy.commerce.exception.ApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.legacy.commerce.logging.OrderLogging)")
    public Object loggingStartEndOfMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        boolean startLogging = true;
        boolean resultLogging = true;
        boolean exceptionLogging = true;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        OrderLogging orderLoggingAnnotation = signature.getMethod().getAnnotation(OrderLogging.class);

        if ( Objects.nonNull(orderLoggingAnnotation) && Objects.nonNull(orderLoggingAnnotation.excludes()) ) {
            for ( int excludePoint : orderLoggingAnnotation.excludes() ) {
                if ( excludePoint == OrderLogging.SYSTART ) {
                    startLogging = false;
                }
                else if ( excludePoint == OrderLogging.RESULT ) {
                    resultLogging = false;
                }
                else if ( excludePoint == OrderLogging.EXCEPTION ) {
                    exceptionLogging = false;
                }
            }
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if ( startLogging ) {
           /* logger.warn("###API_START<{}> Method<{}> Uri<{}> args<{}>",
                    joinPoint.getSignature().toShortString(),
                    request.getMethod(),
                    request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
                    JsonUtils.convertObjToJsonWithNullsafe(joinPoint.getArgs())
            );*/

            logger.warn("###API_START<{}> Method<{}> Uri<{}> args<{}>",
                    joinPoint.getSignature().toShortString(),
                    request.getMethod(),
                    request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "")
            );
        }

        //실행속도 측정하기
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object retVal = null;

        try {
            retVal = joinPoint.proceed();
        }
        catch (Exception ex) {
            stopWatch.stop();
            if ( ex instanceof ApiException) {
                if ( ((ApiException)ex).isLoggable() == false) {
                    ApiException ae = (ApiException)ex;
                    logger.warn("notLoggableException -> ApiException :: uri :: {} :: code -> {} , msg -> {} , lMsg -> {} , info -> {} , cause -> {}, trace -> {}",
                            request.getRequestURI(), ae.getExceptionCode(), ae.getMessage(), ae.getLocalizedMessage(), ae.getExceptionInfo(), ae.getCause(), ae, ae);
                }
                logger.warn("###API_Exception<{}> Method<{}> Uri<{}> ExceptionClass<{}> ExceptionCode<{}> ExceptionMessage<{}> ExceptionInfo<{}> runTime<{}ms> args<{}> ",
                        joinPoint.getSignature().toShortString(),
                        request.getMethod(),
                        request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
                        ex.getClass(),
                        ex instanceof ApiException ? ((ApiException) ex).getExceptionCode() : "SYS",
                        ex.getMessage(),
                        ex instanceof ApiException ? ((ApiException) ex).getExceptionInfo() : "",
                        stopWatch.getTotalTimeMillis(),
                        JsonUtils.convertObjToJsonWithNullsafe(joinPoint.getArgs()),
                        ex
                );

                if (ex instanceof ApiException && ((ApiException) ex).getExceptionInfo() instanceof Map) {
                    Map<String, Object> exceptionInfoMap = (Map<String, Object>) ((ApiException) ex).getExceptionInfo();
                    exceptionInfoMap.remove("ErrorDetailMessage");
                }
            }
            throw ex;
        }
        stopWatch.stop();

       /* logger.warn("###API_END<{}> Method<{}> Uri<{}> args<{}> Output<{}> runTime<{}ms ",
                joinPoint.getSignature().toShortString(),
                request.getMethod(),
                request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
                JsonUtils.convertObjToJsonWithNullsafe(joinPoint.getArgs()),
                resultLogging ? JsonUtils.convertObjToJsonWithNullsafe(retVal) : "-SKIP-",
                stopWatch.getTotalTimeMillis()
        );*/

        logger.warn("###API_END<{}> Method<{}> Uri<{}> args<{}> Output<{}> runTime<{}ms ",
                joinPoint.getSignature().toShortString(),
                request.getMethod(),
                request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""),
                stopWatch.getTotalTimeMillis()
        );

        return retVal;
    }
}
