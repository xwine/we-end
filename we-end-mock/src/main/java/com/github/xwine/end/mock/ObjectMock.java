package com.github.xwine.end.mock;

import com.github.xwine.end.mock.util.CacheUtils;
import com.github.xwine.end.mock.util.ObjectUtils;

import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-08 10:37
 */
public final class ObjectMock {

    /**
     * The deep of auto mock data
     */
    private static final Integer objectDeep = 2;

    /**
     * Class-Object
     * @param type
     * @return
     */
    public static Object getObject(Type type) {
        return getObject(type, objectDeep);
    }

    /**
     * Fetch object from mock.json
     * @param cacheId
     * @param type
     * @return
     */
    public static Object getObjectAndCache(String cacheId, Type type) {
        return getObjectAndCache(cacheId,type,objectDeep);
    }


    /**
     * 根据Class生成Object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseClass(Class<T> clazz) {
        Object object = getObject(clazz);
        return (T)object;
    }

    /**
     * 根据Class生成Object并缓存
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseClassAndCache(String cacheId, Class<T> clazz) {
        Object object = getObjectAndCache(cacheId,clazz);
        return (T)object;
    }

    /**
     * Class-Object
     * @param type
     * @return
     */
    public static Object getObject(Type type, int deep) {
        //本地缓存开启
        if (MockContext.getConfig().isLocalCacheSwitch()) {
            Object obj = CacheUtils.get(type);
            if (obj == null) {
                //缓存为空，自动生成并缓存起来
                obj = ObjectUtils.getObject(type,deep);
                CacheUtils.put(type,obj);
            }
            return obj;
        }
        return ObjectUtils.getObject(type, deep);
    }

    /**
     * Fetch object from .mock dir
     * @param cacheId
     * @param type
     * @return
     */
    public static Object getObjectAndCache(String cacheId, Type type, int deep) {
        Object object = CacheUtils.get(cacheId,type);
        if (object == null) {
            object = getObject(type,deep);
            CacheUtils.put(cacheId,object,type);
        }
        return object;
    }


    /**
     * 根据Class生成Object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseClass(Class<T> clazz, int deep) {
        Object object = getObject(clazz,deep);
        return (T)object;
    }

    /**
     * 根据Class生成Object并缓存
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseClassAndCache(String cacheId, Class<T> clazz, int deep) {
        Object object = getObjectAndCache(cacheId,clazz,deep);
        return (T)object;
    }

    public static void main(String[] args) {
        System.out.println(".___  ___.   ______     ______  __  ___ \n" +
                "|   \\/   |  /  __  \\   /      ||  |/  / \n" +
                "|  \\  /  | |  |  |  | |  ,----'|  '  /  \n" +
                "|  |\\/|  | |  |  |  | |  |     |    <   \n" +
                "|  |  |  | |  `--'  | |  `----.|  .  \\  \n" +
                "|__|  |__|  \\______/   \\______||__|\\__\\ \n" +
                "                                        ");
    }
}
