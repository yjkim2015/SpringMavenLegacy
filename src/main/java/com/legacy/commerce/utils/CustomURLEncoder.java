package com.legacy.commerce.utils;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CustomURLEncoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomURLEncoder.class);

    public static String encode(String value, String charEncoding) {
        try {
            return URLEncoder.encode(value, charEncoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(Throwables.getStackTraceAsString(e));
            throw new IllegalArgumentException(e);
        }
    }

    public static String encode(String value) { return encode(value, "utf-8");}
}
