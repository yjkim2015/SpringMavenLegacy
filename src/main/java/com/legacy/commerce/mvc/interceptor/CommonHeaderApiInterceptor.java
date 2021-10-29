package com.legacy.commerce.mvc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legacy.commerce.rest.model.header.ApiCommonHeader;
import com.legacy.commerce.rest.model.header.ApiCommonHeaderSupport;
import com.legacy.commerce.utils.CustomURLDecorder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonHeaderApiInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    protected  final Logger logger = LoggerFactory.getLogger(getClass());

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String header = request.getHeader("apiCommon");
            ApiCommonHeader apiCommonHeader = new ApiCommonHeader();
            if (StringUtils.isNotEmpty(header)) {
                apiCommonHeader = objectMapper.readValue(CustomURLDecorder.decode(header), ApiCommonHeader.class);
            }
            request.setAttribute(ApiCommonHeader.API_COMMON_HEADER_REQUEST_KEY, apiCommonHeader);
            ApiCommonHeaderSupport headerSupport = new ApiCommonHeaderSupport();
            headerSupport.setAllAbTypes(apiCommonHeader.getAbType());
            apiCommonHeader.setHeaderSupport(headerSupport);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
