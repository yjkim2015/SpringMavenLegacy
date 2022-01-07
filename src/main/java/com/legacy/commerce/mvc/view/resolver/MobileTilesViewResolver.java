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

public class MobileTilesViewResolver extends UrlBasedViewResolver implements InitializingBean {
    protected static Logger LOGGER = LoggerFactory.getLogger(MobileTilesViewResolver.class);

    public MobileTilesViewResolver() {
        setViewClass(MobileTilesView.class);
        setPrefix("/__mobile");
    }

    @Override
    protected Object getCacheKey(String viewName, Locale locale) {

        HttpServletRequest servletRequest = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        String prefix = "";

        SitePreference service = SitePreferenceUtils.getCurrentSitePreference(servletRequest);
        if (service != null && service.isMobile()) {
            prefix = getPrefix();
        }

        return prefix + super.getCacheKey(viewName, locale);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getViewClass() == null) {
            setViewClass(MobileTilesView.class);
        }

    }

}
