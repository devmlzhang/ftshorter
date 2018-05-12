package com.yicheng.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 * @author luo.yicheng
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转Json
     *
     * @param object
     * @return String
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
