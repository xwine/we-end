package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-09 15:27
 */
public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static Gson gson = null;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy());
        builder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
        // 支持时间戳转化
//        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                return new Date(json.getAsJsonPrimitive().getAsLong());
//            }
//        });
        builder.setLenient();
        gson = builder.create();
    }

    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    public static String beautyJson(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public static Object fromJson(JsonElement jsonElement, Type type) {
        if (jsonElement == null) {
            return null;
        }
        try {
            return gson.fromJson(jsonElement, type);
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]json convert exception :{}",e.getMessage());
            return null;
        }
    }

    public static JsonElement toJsonTree (Object object, Type type) {
        if (object == null) {
            return null;
        }
        try {
            return gson.toJsonTree(object, type);
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]json convert exception :{}",e.getMessage());
            return null;
        }
    }

    public static JsonElement toJsonTree (Object object) {
        if (object == null) {
            return null;
        }
        try {
            return gson.toJsonTree(object);
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]json convert exception :{}",e.getMessage());
            return null;
        }
    }

    public static JsonElement parseJson(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
            return jsonElement;
        } catch (JsonParseException e) {
            MockContext.LOG.error("[O-MOCK]json convert exception:{}",e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
//        Date d = gson.fromJson("1575666099000", Date.class);
//        Date d =  gson.fromJson("20100222", Date.class);
//        System.out.println(d.getYear());
    }
}

/**
 * 排除父子类属性重复定义的异常
 */
class SuperclassExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        String fieldName = fieldAttributes.getName();
        Class<?> theClass = fieldAttributes.getDeclaringClass();

        return isFieldInSuperclass(theClass, fieldName);
    }

    private boolean isFieldInSuperclass(Class<?> subclass, String fieldName) {
        Class<?> superclass = subclass.getSuperclass();
        Field field;

        while (superclass != null) {
            field = getField(superclass, fieldName);

            if (field != null) {
                return true;
            }

            superclass = superclass.getSuperclass();
        }

        return false;
    }

    private Field getField(Class<?> theClass, String fieldName) {
        try {
            return theClass.getDeclaredField(fieldName);
        } catch (Exception e) {
            return null;
        }
    }

}
