package com.legacy.commerce.mvc.filter;


import com.legacy.commerce.mvc.request.CustomizedHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomizedHttpServletRequestFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedHttpServletRequestFilter.class);
    private String ipHeaderName;
    private String dummyIp;
    private String DEFAULT_IP = "X_Forwarded-IP";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ipHeaderName = filterConfig.getInitParameter("ip");
        if (StringUtils.isEmpty(ipHeaderName) ) {
            ipHeaderName = DEFAULT_IP;
        }

        dummyIp = filterConfig.getInitParameter("dummyIp");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.warn("<<<<<<<<<<doFilter start>>>>>>>>>>");
        String cts = request.getContentType();
        if (cts == null || !cts.toLowerCase().startsWith("multipart/")) {
            chain.doFilter(new CustomizedHttpServletRequest((HttpServletRequest) request, ipHeaderName, dummyIp), response);
        } else {
            chain.doFilter(new StandardMultipartHttpServletRequest((HttpServletRequest) request, false), response);
        }
        logger.warn("<<<<<<<<<<doFilter end>>>>>>>>>>");

    }

    @Override
    public void destroy() {
        logger.warn("<<<<<<<<<<filter destroy>>>>>>>>>>");

    }
}
