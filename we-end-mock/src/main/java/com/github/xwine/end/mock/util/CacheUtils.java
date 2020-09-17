package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-05 19:22
 */
public class CacheUtils {

    private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);

    public static Object get(Type type) {
        try {
            String name = type.toString();
            JsonElement jsonElement = FileUtils.getObjectFromFile(EnvUtils.getBasicMockFilePath(name));
            if (jsonElement != null) {
                return JsonUtils.fromJson(jsonElement, type);
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]read object from file error:type:{}]",type.toString());
        }
        return null;
    }

    public static Object get(String cacheId, Type type) {
        JsonElement jsonElement = FileUtils.getObjectFromFile(EnvUtils.getMockCacheIdFile(cacheId));
        if (jsonElement != null) {
            return JsonUtils.fromJson(jsonElement, type);
        }
        return null;
    }

    public static void put(String cacheId, Object object,Type type) {
        JsonElement jsonElement = JsonUtils.toJsonTree(object, type);
        FileUtils.saveJsonToFile(jsonElement,EnvUtils.getMockCacheIdFile(cacheId));
    }

    public static void put(Type type, Object object) {
        String name = type.toString();
        JsonElement jsonElement = JsonUtils.toJsonTree(object, type);
        FileUtils.saveJsonToFile(jsonElement,EnvUtils.getBasicMockFilePath(name));
    }
}
