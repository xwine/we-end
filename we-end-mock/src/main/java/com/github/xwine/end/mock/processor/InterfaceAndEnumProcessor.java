package com.github.xwine.end.mock.processor;

import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-07 09:04
 */
public class InterfaceAndEnumProcessor  extends DefaultProcessor {

    public InterfaceAndEnumProcessor(Type type) {
        super(type);
    }

    public InterfaceAndEnumProcessor(Type type, int deep) {
        super(type, deep);
    }

    public InterfaceAndEnumProcessor(int deep, Type type, Type genType) {
        super(deep, type, genType);
    }

    @Override
    public Object process() {
        return super.process();
    }
}