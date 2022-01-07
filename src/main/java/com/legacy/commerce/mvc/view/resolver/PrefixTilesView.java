package com.legacy.commerce.mvc.view.resolver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.tiles3.TilesView;

import javax.servlet.http.HttpServletRequest;

public abstract class PrefixTilesView extends TilesView {
    protected static Logger LOGGER = LoggerFactory.getLogger(PrefixTilesView.class);

    String prefix;

    protected String getUrl(HttpServletRequest servletRequest) {

        try {
            if (isValid(servletRequest) && StringUtils.isNotEmpty(prefix)) {
                return prefix + super.getUrl();
            } else {
                return super.getUrl();
            }

        } catch (NullPointerException e) {
            LOGGER.error("please , add deviceResolverRequestFilter to web.xml. see skeleton_web/web.xml");
            return super.getUrl();
        } catch (Exception e) {
            LOGGER.debug("getUrl(HttpServletRequest servletRequest)  error : {}", e);
            return super.getUrl();
        }
    }

    public abstract boolean isValid(HttpServletRequest servletRequest);

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
