package com.legacy.commerce.utils;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CustomURLDecorder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomURLDecorder.class);

    public static String decode(String value, String charEncoding) {
        try {
            return URLDecoder.decode(value, charEncoding);
        }
        catch (UnsupportedEncodingException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            throw new IllegalStateException(e);
        }
    }

    public static String decode(String value) { return decode(value, "utf-8"); }
}
