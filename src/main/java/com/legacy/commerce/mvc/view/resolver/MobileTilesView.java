package com.legacy.commerce.mvc.view.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class MobileTilesView extends PrefixTilesView {
    protected static Logger LOGGER = LoggerFactory.getLogger(MobileTilesView.class);

    @Override
    public boolean checkResource(final Locale locale) throws Exception {
        HttpServletRequest servletRequest = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        SitePreference service = SitePreferenceUtils.getCurrentSitePreference(servletRequest);
        if (service == null || !service.isMobile()) {
            LOGGER.debug("checkResource : {} ", false);
            return false;
        }
        return super.checkResource(locale);
    }

    @Override
    public boolean isValid(HttpServletRequest servletRequest) {
        boolean isMobile = SitePreferenceUtils.getCurrentSitePreference(servletRequest).isMobile();
        LOGGER.debug("isMobile : {} ", isMobile);
        return isMobile;
    }

}
