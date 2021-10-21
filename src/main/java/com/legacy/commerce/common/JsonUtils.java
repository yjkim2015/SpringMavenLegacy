package com.legacy.commerce.common;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.legacy.commerce.utils.Streams;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonUtils {

    public static String toJson(Object value) {

        Writer writer = new StringWriter();
        JsonGenerator jsonGenerator;
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonGenerator = new JsonFactory().createGenerator(writer);
            mapper.writeValue(jsonGenerator, value);
            jsonGenerator.close();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    /**
     * Object to Json => NullPointerException 무시
     * 예외가 무시되어야 하는 곳(로깅 등)에서만 사용
     *
     * @param value
     * @return
     */
    public static String convertObjToJsonWithNullsafe(Object value) throws JsonProcessingException {
        if (Objects.isNull(value) ) return null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().setSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                return Streams.ofNullable(beanProperties).map(bpw -> new BeanPropertyWriter(bpw) {
                    @Override
                    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
                        try {
                            super.serializeAsField(bean, gen, prov);
                        } catch (Exception e) {
                            // 직렬화 시점에서 NullPointerException 이 발생한 경우만 익셉션을 무시한다
                            if ((e.getCause() instanceof NullPointerException) == false) {
                                throw e;
                            }
                        }
                    }
                }).collect(Collectors.toList());
            }
        }));

        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return e.getCause().toString();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertJsonToMap(Object jsonString) {
        if (jsonString == null) {
            return new HashMap<String, Object>();
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> retRequestParamMap = new HashMap<String, Object>();
        try {
            //retRequestParamMap = mapper.readValue((String) jsonString, Map.class);
            retRequestParamMap = mapper.readValue(jsonString.toString(), Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retRequestParamMap;
    }

    /**
     * json을 Object 로 변경
     *
     * @author 김연길
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T convertJsonToObj(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        T t = null;
        try {
            t = mapper.readValue(jsonStr, clazz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return t;
    }

    /**
     * json을 Object 로 변경
     *
     * @author 김연길
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T convertJsonToObject(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        T t = null;
        try {
            t = mapper.readValue(jsonStr, clazz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return t;
    }

    /**
     * json을 List<Object> 로 변경
     *
     * @author 임세홍
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> List<T> convertJsonToObjList(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<T> list = null;

        try {
            list = mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }
}
