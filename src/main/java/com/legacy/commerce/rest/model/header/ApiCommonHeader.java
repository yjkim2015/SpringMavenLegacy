package com.legacy.commerce.rest.model.header;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class ApiCommonHeader extends BaseModelSupport {
    public static final String API_COMMON_HEADER_REQUEST_KEY = "__apiCommonHeader";

    private Map<String, String> abType;

    private ApiCommonUser user;

    private String ip;

    private boolean direct;

    @JsonIgnore
    private ApiCommonHeaderOption option;

    @JsonIgnore
    private ApiCommonHeaderSupport headerSupport;

    public Map<String, String> getAbType() {
        return abType;
    }

    public void setAbType(Map<String, String> abType) {
        this.abType = abType;
    }

    public ApiCommonUser getUser() {
        return user;
    }

    public void setUser(ApiCommonUser user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public ApiCommonHeaderOption getOption() {
        return option;
    }

    public void setOption(ApiCommonHeaderOption option) {
        this.option = option;
    }

    public ApiCommonHeaderSupport getHeaderSupport() {
        return headerSupport;
    }

    public void setHeaderSupport(ApiCommonHeaderSupport headerSupport) {
        this.headerSupport = headerSupport;
    }
}
