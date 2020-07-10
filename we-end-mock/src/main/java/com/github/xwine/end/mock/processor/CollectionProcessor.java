package com.github.xwine.end.mock.processor;

import com.github.xwine.end.mock.util.ObjectUtils;
import com.github.xwine.end.mock.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xwine
 * @date 2020-03-05 11:00
 */
public class CollectionProcessor extends DefaultProcessor {

    public CollectionProcessor(Type type) {
        super(type);
    }

    public CollectionProcessor(Type type, int deep) {
        super(type, deep);
    }

    public CollectionProcessor(int deep, Type type, Type genType) {
        super(deep, type, genType);
    }

    @Override
    public Object process() {
        Object object = null;
        Class<?> rawType = TypeUtils.getRawType(type);
        Type[] actualTypeArgument = TypeUtils.getActualTypeArguments(type);
        //List结构
        if (rawTypeList(rawType)) {
            if (actualTypeArgument.length != 1) {
                throw new RuntimeException("泛型位数不符合要求");
            }
            List<Object> array = new ArrayList<Object>();
            for (int i = 0; i < 3; i++) {
                Type type = actualTypeArgument[0];
                if (genType != null && (type.equals(Object.class) || type.toString().equals("T"))) {
                    //List<T> objs;
                    array.add(ObjectUtils.getObject(genType, deep));
                } else {
                    array.add(ObjectUtils.getObject(type, deep));
                }
            }
            object = array;
        }
        //Map结构
        if (rawTypeMap(rawType)) {
            if (actualTypeArgument.length != 2) {
                throw new RuntimeException("泛型位数不符合要求");
            }
            Map<Object, Object> map = new HashMap<Object, Object>();
            for (int i = 0; i < 3; i++) {
                Type typeKey = actualTypeArgument[0];
                Type typeValue = actualTypeArgument[1];
                map.put(ObjectUtils.getObject(typeKey, deep), ObjectUtils.getObject(typeValue, deep));
            }
            object = map;
        }
        return object;
    }


    private static boolean rawTypeList(Class<?> clazz) {
        if (clazz.equals(List.class) || clazz.equals(ArrayList.class)) {
            return true;
        }
        return false;
    }

    private static boolean rawTypeMap(Class<?> clazz) {
        if (clazz.equals(Map.class) || clazz.equals(HashMap.class)) {
            return true;
        }
        return false;
    }
}
