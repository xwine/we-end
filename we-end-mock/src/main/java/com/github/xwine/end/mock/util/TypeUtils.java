package com.github.xwine.end.mock.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * @author xwine
 * @date 2020-03-05 10:53
 */
public final class TypeUtils {

    private TypeUtils() {
        throw new AssertionError();
    }

    public static boolean isInterfaceOrEnumType(Type type) {
        if (type instanceof Class) {
            Class clazz = (Class) type;
            return clazz.isEnum() || clazz.isInterface();
        }
        return false;
    }

    public static boolean isGenType(Type type) {
        return type.toString().contains("<");
    }

    /**
     * 基础类型判断
     *
     * @param type
     * @return
     * @throws Exception
     */
    public static boolean isBaseDataType(Type type) {
        if (type instanceof Class) {
            Class clazz = (Class) type;
            return  ClassUtils.isBaseDataType(clazz);
        }
        return false;
    }

    public static boolean isArrayDataType(Type type) {
        return (type instanceof Class<?> && ((Class<?>) type).isArray())
                || (type instanceof GenericArrayType);
    }

    public static boolean isCollectionDataType(Type cc) {
        String typeName = cc.toString();
        if (typeName.startsWith(ArrayList.class.getName())
                || typeName.startsWith(List.class.getName())
                || typeName.startsWith(Set.class.getName())
                || typeName.startsWith(HashSet.class.getName())
                || typeName.startsWith(LinkedList.class.getName())
                || typeName.startsWith(HashMap.class.getName())
                || typeName.startsWith(Map.class.getName())) {
            return true;
        }
        return false;
    }


    public static boolean isUseDataType(Type genericType) {
        if (genericType instanceof Class) {
            Class clazz = (Class) genericType;
            return clazz.getClassLoader() != null;
        }
        return false;
    }

    public static Class<?> getRawType(Type type) {
        Utils.checkTrue(isReferenceType(type), "type is not a reference type: ", type);
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            return (Class<?>) rawType;
        }
        return  (Class<?>) type;
    }

    /**
     * A<B> 返回 B
     * @param type
     * @return
     */
    public static Type getActualTypeArgument(Type type) {
        Utils.checkNotNull(type, "type");

        ParameterizedType paramType = (ParameterizedType) type;

        Type[] typeArgs = paramType.getActualTypeArguments();

        Utils.checkTrue(typeArgs.length == 1, "type must be a ParameterizedType with one actual type argument: ", type);

        return typeArgs[0];
    }

    public static Type[] getActualTypeArguments(Type type) {
        Utils.checkNotNull(type, "type");

        ParameterizedType paramType = (ParameterizedType) type;

        Type[] typeArgs = paramType.getActualTypeArguments();

        return typeArgs;
    }

    private static boolean isReferenceType(Type type) {
        return type == null
                || type instanceof Class<?>
                || type instanceof ParameterizedType
                || type instanceof TypeVariable<?>
                || type instanceof GenericArrayType;
    }

    /**
     *  T[] 返回类型 T
     * @param type
     * @return
     */
    public static Type getComponentType(Type type) {
        if (type instanceof Class<?>)
        {
            Class<?> klass = (Class<?>) type;

            return klass.isArray() ? klass.getComponentType() : null;
        }

        if (type instanceof GenericArrayType)
        {
            return ((GenericArrayType) type).getGenericComponentType();
        }

        return null;
    }
}
