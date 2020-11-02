package com.github.xwine.end.mock.processor;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.util.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-05 09:50
 */
public class ObjectProcessor  extends DefaultProcessor {

    public ObjectProcessor(Type type) {
        super(type);
    }

    public ObjectProcessor(Type type, int deep) {
        super(type);
        this.deep = deep;
    }

    public ObjectProcessor(int deep, Type type, Type genType) {
        super(deep, type, genType);
    }

    @Override
    public Object process() {
        Object object = null;
        if (type instanceof Class) {
            Class clazz  = (Class) type;
            try {
                try {
                    object = clazz.newInstance();
                } catch (Exception e) {
                    object = ClassUtils.construct(clazz);
                }
                if (object == null) {
                    return null;
                }
                Class superclass = clazz.getSuperclass();
                if (superclass != null && !(Object.class.equals(superclass))) {
                    Field[] superclassDeclaredFields = superclass.getDeclaredFields();
                    objectFields(superclass, object, superclassDeclaredFields);
                }
                Field[] fields = clazz.getDeclaredFields();
                objectFields(clazz, object, fields);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    private void objectFields(Class clazz, Object obj, Field[] fields) throws Exception {
        String fieldSetMethodName;
        Object fieldValue = null;
        for (Field field : fields) {
            //排除静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // 设置为可访问
            field.setAccessible(true);
            // 获取属性对应的设置方法名
            if (field.getType().equals(Object.class) && genType != null) {
                //private T obj;
                fieldValue = ObjectUtils.getObject(genType, deep,field.getName());
            } else if (genType != null &&
                    TypeUtils.isCollectionDataType(field.getGenericType()) &&
                    field.getGenericType().toString().contains("<T")) {
                Processor processor = new CollectionProcessor(2, field.getGenericType(), genType);
                fieldValue = processor.process();
            } else {
                fieldValue = ObjectUtils.getObject(field.getGenericType(), deep,field.getName());
            }
            // 获取属性对应的设置方法名 先调用set方法，如果失败 直接设置值
            Method method = null;
            try {
                field.set(obj,fieldValue);
                continue;
            } catch (Exception e) {
                try {
                    fieldSetMethodName = "set" + StringUtils.upperCase(field.getName());
                    method = clazz.getDeclaredMethod(fieldSetMethodName, field.getType());
                } catch (NoSuchMethodException exception) {
                    // file is begin with is
                    fieldSetMethodName = "set" + StringUtils.upperCaseAndRemove(field.getName());
                    method = clazz.getDeclaredMethod(fieldSetMethodName, field.getType());
                }
            }
            method.invoke(obj, fieldValue);
        }
    }
}

