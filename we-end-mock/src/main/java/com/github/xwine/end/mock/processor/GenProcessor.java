package com.github.xwine.end.mock.processor;

import com.github.xwine.end.mock.util.TypeUtils;

import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-05 22:35
 */
public class GenProcessor extends DefaultProcessor {

    public GenProcessor(Type type) {
        super(type);
    }

    public GenProcessor(Type type, int deep) {
        super(type, deep);
    }

    public GenProcessor(int deep, Type type, Type genType) {
        super(deep, type, genType);
    }

    @Override
    public Object process() {
        Object obj = null;
        try {

            Class<?> rawClass = TypeUtils.getRawType(type);
            genType = TypeUtils.getActualTypeArgument(type);
            obj = new ObjectProcessor(deep,rawClass,genType).process();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
