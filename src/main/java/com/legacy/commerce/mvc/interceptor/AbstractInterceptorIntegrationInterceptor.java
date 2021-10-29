package com.legacy.commerce.mvc.interceptor;

import com.google.common.collect.Maps;
import com.legacy.commerce.utils.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractInterceptorIntegrationInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
    protected  final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationContext context;

    List<? extends HandlerInterceptor> interceptors;

    @SuppressWarnings("rawtypes")
    Map<Class<?>, Consumer> adapterMap = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() {
        registerAdapters();
        interceptors = Streams.ofNullable(setInterceptors())
                .map(interceptors -> context.getAutowireCapableBeanFactory().createBean(interceptors))
                .peek(bean -> adapterMap.computeIfPresent(((HandlerInterceptor) bean).getClass(), (k, v) -> {
                    v.accept(bean);
                    return v;
                })).collect(Collectors.toList());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.warn("@@ preHandle request = [{}], response = [{}], handler = [{}]", request, response, handler);

        for (HandlerInterceptor interceptor : interceptors) {
            if ( !interceptor.preHandle(request,response,handler) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.warn("@@ postHandle request = [{}], response = [{}], handler = [{}], modelAndView = [{}]", request, response, handler, modelAndView);
        for (HandlerInterceptor interceptor : interceptors) {
            interceptor.postHandle(request, response, handler, modelAndView);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.warn("@@ afterCompletion request = [{}], response = [{}], handler = [{}], ex = [{}]", request, response, handler, ex);
        for (HandlerInterceptor interceptor : interceptors) {
            interceptor.afterCompletion(request, response, handler, ex);
        }
    }

    protected abstract List<Class<? extends HandlerInterceptor>> setInterceptors();

    protected void registerAdapters() {

    }

    protected <T> void registerAdapter(Class<T> clazz, Consumer<T> consumer) { adapterMap.put(clazz, consumer);}
}

