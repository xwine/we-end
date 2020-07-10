package com.github.xwine.end.mock.processor;

import com.github.xwine.end.mock.util.ObjectUtils;
import com.github.xwine.end.mock.util.TypeUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-05 09:50
 */
public class ArrayProcessor extends DefaultProcessor {
    public int size = 2;

    public ArrayProcessor(Type type) {
        super(type);
    }

    public ArrayProcessor(Type type, int deep) {
        super(type, deep);
    }

    /**
     * @return
     */
    @Override
    public Object process() {
        Type componentType = TypeUtils.getComponentType(type);
        if (componentType instanceof Class) {
            Class clazz = (Class) componentType;
            Object result = Array.newInstance(clazz, size);
            for (int i = 0; i < size; i++) {
                Array.set(result, i, ObjectUtils.getObject(componentType, deep));
            }
            return result;
        }
        return super.process();
    }
}