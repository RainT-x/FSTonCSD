package cn.zitsong.whu.utils;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;


/**
 * @Description: Tools for json transfer
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2019-08-16 10:58
 * @Since: version 1.0.0
 **/
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String object2String(T object) {

        if (object == null) {
            return null;
        }
        try {
            return object instanceof String ? (String)object : objectMapper.writeValueAsString(object);
        } catch (Exception e) {
//            log.warn("parse Object to String Exception error :{}", e);
            return null;
        }
    }

    public static <T> T string2Object(String str, TypeReference<T> typeReference) {

        if (str == null || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class) ? typeReference : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
//            log.warn("parse String to Object Exception String:{}, TypeReference<T>:{}, error:{}", str, typeReference.getType(), e);
            return null;
        }
    }

    public static <T> T string2Object(String str, Class<?> collectionsClass, Class<?>... elementClass) {

        if (str == null) {
            return null;
        }

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionsClass, elementClass);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
//            log.warn("parse String to Object Exception errors:{}", e);
            return null;
        }
    }
}
