package com.github.xwine.end.mock.processor;

import com.github.xwine.end.mock.util.ClassUtils;

import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-02-18 14:08
 */
public class DefaultProcessor implements Processor {

    public int deep = 1;
    public Type type;
    public Type genType;

    public DefaultProcessor(Type type) {
        this.type = type;
    }
    public DefaultProcessor(Type type, int deep) {
        this.type = type;
        this.deep = deep;
    }

    public DefaultProcessor(int deep, Type type, Type genType) {
        this.deep = deep;
        this.type = type;
        this.genType = genType;
    }

    public Object process() {
        return ClassUtils.initBaseNullData(type);
    }
}
