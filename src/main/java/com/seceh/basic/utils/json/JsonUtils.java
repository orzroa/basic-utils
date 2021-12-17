package com.seceh.basic.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seceh.basic.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class.getName());

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private JsonUtils() {
    }

    /**
     * 类或者集合转成json字符串
     * @param object 需要转的对象
     * @param <T> 对象类型
     * @return 单行json字符串
     */
    public static <T> String object2Line(T object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("对象转String发生错误", e);
        }
        return null;
    }

    /**
     * 类或者集合转成json字符串
     * @param object 需要转的对象
     * @param <T> 对象类型
     * @return 多行json字符串
     */
    public static <T> String object2Lines(T object) {

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            logger.error("对象转String发生错误", e);
        }
        return null;
    }

    /**
     * json字符串转成普通对象
     * @param json 需要转的json字符串
     * @param object 转换目标
     * @param <T> 目标类型
     * @return 目标对象
     */
    public static <T> T string2Object(String json, Class<T> object) {

        try {
            return objectMapper.readValue(json, object);
        } catch (Exception e) {
            logger.error("String转对象发生错误", e);
        }

        return null;
    }

    /**
     * json字符串转成泛型相关对象
     * 使用示例：{@code string2Type(param, new TypeReference<List<User>>() {})}
     * @param json 需要转的json字符串
     * @param typeReference 转换目标
     * @param <T> 目标类型
     * @return 目标对象
     */
    public static <T> T string2Type(String json, TypeReference<T> typeReference) {

        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            logger.error("String转泛型相关对象发生错误", e);
        }

        return null;
    }

    public static <T> T resource2Type(String jsonName, TypeReference<T> typeReference) {
        String str = FileUtils.getStringFromResource(jsonName + ".json");
        if (str == null) {
            return null;
        }
        return string2Type(str, typeReference);
    }
}
