package com.legacy.commerce.mvc.interceptor;

import com.google.common.collect.Lists;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

public class ApiInterceptorIntegrationInterceptor extends AbstractInterceptorIntegrationInterceptor {

    @Override
    protected List<Class<? extends HandlerInterceptor>> setInterceptors() {
        List<Class<? extends HandlerInterceptor>> interceptors = Lists.newArrayList();

        interceptors.add(CommonHeaderApiInterceptor.class);
       /* interceptors.add(ApiRequestDirectSupportInterceptor.class);
        interceptors.add(GatewaySupportInterceptor.class);
        interceptors.add(ApiLoggerInterceptor.class);
        interceptors.add(ShardContextInterceptor.class);*/
        return interceptors;
    }
}
