package com.github.xwine.end.spring.aop;

import com.github.xwine.end.mock.annotation.Mock;
import com.github.xwine.end.mock.MockContext;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class MockMethodPointcut  extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Annotation[] annotations = targetClass.getAnnotations();
        for (Annotation annotation : annotations) {
           if (annotation.annotationType().equals(Mock.class)) {
               return true;
           }
        }
        String mid = targetClass.getCanonicalName()+"_"+method.getName();
        List<String> mockClasses = MockContext.getConfig().getMockClasses();
        if (mockClasses == null || mockClasses.size() <=0) {
            return false;
        }
        for (String mockClass : mockClasses) {
            if (mid.startsWith(mockClass)) {
                return true;
            }
        }
        return false;
    }
}
