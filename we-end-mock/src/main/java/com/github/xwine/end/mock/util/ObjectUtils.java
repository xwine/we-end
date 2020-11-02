package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.processor.*;

import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-08 10:15
 */
public final class ObjectUtils {

    private ObjectUtils() {
        throw new AssertionError();
    }

    /**
     * Type-Object
     * @param type
     * @param objectDeep
     * @return
     */
    public static Object getObject(Type type, int objectDeep, String name) {
        if (objectDeep <= 0) {
            return new DefaultProcessor(type).process();
        }
        if (TypeUtils.isBaseDataType(type)) {
            return new BasicProcessor(type, objectDeep,name).process();
        }
        if (TypeUtils.isArrayDataType(type)) {
            return new ArrayProcessor(type, objectDeep).process();
        }
        if (TypeUtils.isCollectionDataType(type)) {
            return new CollectionProcessor(type, objectDeep).process();
        }
        if (TypeUtils.isInterfaceOrEnumType(type)) {
            return new InterfaceAndEnumProcessor(type, objectDeep - 1).process();
        }
        if (TypeUtils.isUseDataType(type)) {
            return new ObjectProcessor(type, objectDeep - 1).process();
        }
        //泛型
        if (TypeUtils.isGenType(type)) {
            return new GenProcessor(type, objectDeep - 1).process();
        }
        return new DefaultProcessor(type).process();
    }

    public static Object getObject(Type type, int objectDeep) {
        return getObject(type,objectDeep,null);
    }

}
