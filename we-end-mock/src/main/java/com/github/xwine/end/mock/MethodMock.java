package com.github.xwine.end.mock;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class MethodMock {

    public static Object getObject(String className,String methodName) {
        try {
            Class<?> aClass = Class.forName(className);
            Method[] methods = aClass.getMethods();
            for (Method m : methods) {
                //暂不支持方法重载
                if (m.getName().equals(methodName)) {
                    String id = className + "_" + methodName;
                    Type type = m.getAnnotatedReturnType().getType();
                    Object object = ObjectMock.getObjectAndCache(id,type, MockContext.getConfig().getDeep());
                    return object;
                }
            }
            MockContext.LOG.error("[O-MOCK] MethodMock.getObject.cannot find method of class :class:{},method:{}",className,methodName);
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] MockMethodAdvise.invoke exception：");
        }
        return null;
    }
}
