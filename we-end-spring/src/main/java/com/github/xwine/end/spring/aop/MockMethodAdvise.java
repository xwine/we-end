package com.github.xwine.end.spring.aop;

import com.github.xwine.end.mock.MethodMock;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.util.PropertiesUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class MockMethodAdvise implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (!MockContext.getConfig().getMockOn()) {
            return methodInvocation.proceed();
        }
        Object obj = null;
        Method method = methodInvocation.getMethod();
        if (method != null ) {
            String className = method.getDeclaringClass().getCanonicalName();
            String methodName = method.getName();
            obj = MethodMock.getObject(className,methodName);
        }
        return obj;
    }
}
