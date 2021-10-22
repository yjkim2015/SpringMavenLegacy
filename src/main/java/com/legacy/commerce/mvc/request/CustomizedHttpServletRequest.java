package com.legacy.commerce.mvc.request;

import com.google.common.collect.Iterables;
import com.google.common.collect.ObjectArrays;
import com.legacy.commerce.utils.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;

public class CustomizedHttpServletRequest extends HttpServletRequestWrapper {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private String ipHeaderName;
    private String dummyIp;
    private byte[] body;

    public CustomizedHttpServletRequest(HttpServletRequest request, String ipHeaderName) throws IOException {
        this(request, ipHeaderName, null);
    }

    public CustomizedHttpServletRequest(HttpServletRequest request, String ipHeaderName, String dummyIp) throws IOException {
        super(request);
        this.ipHeaderName = ipHeaderName;
        this.dummyIp = dummyIp;
        this.body = IOUtils.toByteArray(super.getInputStream());
    }

    @Override
    public String getRemoteAddr() {

        if (StringUtils.isNotEmpty(dummyIp)) {
            return dummyIp;
        }
        String ip = super.getHeader(ipHeaderName);
        return StringUtils.isEmpty(ip) ? super.getRemoteAddr() : ip;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomizedServletInputStream(new ByteArrayInputStream(this.body));
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Iterable<NameValuePair> params = new ArrayList<>();

        if(getQueryString() != null) {
            params = URLEncodedUtils.parse(getQueryString(), Charset.defaultCharset());
        }
        try {
            String cts = getContentType();
            if (cts != null) {
                ContentType ct = ContentType.parse(cts);
                if (ct.getMimeType().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
                    String rawBody = IOUtils.toString(getReader());

                    MultiValueMap<String, String> queryParams = UriComponentsBuilder.newInstance().query(rawBody).build().getQueryParams();
                    List<NameValuePair> postParams = new ArrayList<>();
                    Streams.ofNullable(queryParams.entrySet()).forEach(entry -> {
                        String key = entry.getKey();
                        entry.getValue().stream().forEach(data -> {
                            try {
                                String valueDecode = URLDecoder.decode(data, "UTF-8");
                                String keyDecode = URLDecoder.decode(key, "UTF-8");
                                postParams.add(new BasicNameValuePair(keyDecode, valueDecode));
                            } catch (Exception e) {
                                // decode시 오류가 나면 무조건적으로 추가 한다
                                postParams.add(new BasicNameValuePair(key, data));
                            }

                        });
                    });
                    params = Iterables.concat(params, postParams);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Map<String, String[]> result = toMap(params);

        super.getParameterMap().forEach(result::putIfAbsent);

        return result;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public String getParameter(String name) {
        Map<String, String[]> params = getParameterMap();
        String[] result = params.get(name);
        if(result == null || result.length <= 0) {
            return null;
        }
        return result[0];
    }

    @Override
    public Enumeration getParameterNames() {
        return new Enumeration<String>() {
            private String[] arr = (String[])(getParameterMap().keySet().toArray(new String[0]));
            private int index = 0;

            @Override
            public boolean hasMoreElements() {
                return index < arr.length;
            }

            @Override
            public String nextElement() {
                return arr[index++];
            }
        };
    }

    @Override
    public String[] getParameterValues(String name) {
        Map<String, String[]> params = getParameterMap();
        String[] arr = params.get(name);
        if (arr == null) {
            return null;
        }
        return arr;
    }

    public static Map<String, String[]> toMap(Iterable<NameValuePair> body) {
        Map<String, String[]> result = new LinkedHashMap<>();
        for (NameValuePair e : body) {
            String key = e.getName();
            String value = e.getValue();
            if (result.containsKey(key)) {
                String[] newValue = ObjectArrays.concat(result.get(key), value);
                result.remove(key);
                result.put(key, newValue);
            } else {
                result.put(key, new String[] {value});
            }
        }
        return result;
    }
}
