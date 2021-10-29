package com.legacy.commerce.rest.model.header;

import java.util.Map;

public class ApiCommonHeaderSupport extends BaseModelSupport {
    Map<String, String> allAbTypes;

    public Map<String, String> getAllAbTypes() { return allAbTypes; }

    public void setAllAbTypes(Map<String, String> allAbTypes) { this.allAbTypes = allAbTypes; }
}
