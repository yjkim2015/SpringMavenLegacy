package com.legacy.commerce.mvc.view.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class NormalTilesViewResolver extends UrlBasedViewResolver implements InitializingBean {
    protected static Logger LOGGER = LoggerFactory.getLogger(NormalTilesViewResolver.class);

    public NormalTilesViewResolver() {
        setViewClass(NormalTilesView.class);
    }

    @Override
    protected Object getCacheKey(String viewName, Locale locale) {

        System.out.println("씨뿌레것");
        HttpServletRequest servletRequest = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        String prefix = "";
        SitePreference service = SitePreferenceUtils.getCurrentSitePreference(servletRequest);
        if (service != null && service.isMobile()) {
            prefix = "/__mobile";
        }

        System.out.println(prefix + super.getCacheKey(viewName, locale));

        return prefix + super.getCacheKey(viewName, locale);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getViewClass() == null) {
            setViewClass(NormalTilesView.class);
        }

    }

}
