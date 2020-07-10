package com.github.xwine.end.mock.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author xwine
 * @date 2020-03-06 12:19
 */
public class ClassUtils {

    /**
     * 解决类不含无参数构造情况
     * @param rawClass
     * @return
     */
    public static Object construct(Class rawClass) {
        Object obj = null;
        Constructor<?>[] declaredConstructors = rawClass.getDeclaredConstructors();
        if (declaredConstructors != null && declaredConstructors.length>0) {
            Constructor<?> firstConstructor = declaredConstructors[0];
            firstConstructor.setAccessible(true);
            Type[] genericParameterTypes = firstConstructor.getGenericParameterTypes();
            Object[] params = new Object[genericParameterTypes.length];
            for (int i = 0; i < genericParameterTypes.length; i++) {
                Object object = ObjectUtils.getObject(genericParameterTypes[i], 2);
                params[i] = object;
            }
            try {
                obj = firstConstructor.newInstance(params);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 基础类型判断
     * @param clazz
     * @return
     * @throws Exception
     */
    public static boolean isBaseDataType(Class clazz) {
        return
                (
                        clazz.equals(String.class) ||
                                clazz.equals(Integer.class)||
                                clazz.equals(Byte.class) ||
                                clazz.equals(Long.class) ||
                                clazz.equals(Double.class) ||
                                clazz.equals(Float.class) ||
                                clazz.equals(Character.class) ||
                                clazz.equals(Short.class) ||
                                clazz.equals(BigDecimal.class) ||
                                clazz.equals(BigInteger.class) ||
                                clazz.equals(Boolean.class) ||
                                clazz.equals(Date.class) ||
                                clazz.isPrimitive()
                );
    }

    /**
     * 基本类型默认值
     * @param type
     * @return
     */
    public static Object initBaseNullData(Type type) {
        if (type instanceof Class) {
            Class clazz = (Class) type;
            if (clazz.isPrimitive()) {
                if (type == int.class) {
                    return 0;
                } else if (type == float.class) {
                    return 0f;
                } else if (type == long.class) {
                    return 0L;
                } else if (type == double.class) {
                    return 0D;
                } else if (type == boolean.class) {
                    return false;
                } else if (type == byte.class) {
                    return new Byte("0");
                } else if (type == char.class) {
                    return '0';
                }
            }
        }
        return null;
    }

    public static Class<?> getArrayType(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }
}
