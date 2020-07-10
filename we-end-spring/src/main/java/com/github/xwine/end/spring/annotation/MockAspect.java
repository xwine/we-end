package com.github.xwine.end.spring.annotation;

import com.github.xwine.end.mock.ObjectMock;
import com.github.xwine.end.mock.MockContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author xwine
 * @date 2020-03-07 20:19
 */
@Aspect
@Component
public class MockAspect {

    @Around(value = "(execution(public * ((@com.github.xwine.end.mock.annotation.Mock *)+).*(..)) && within(@com.github.xwine.end.mock.annotation.Mock *)) || @annotation(com.github.xwine.end.mock.annotation.Mock)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (!MockContext.getConfig().getMockOn()) {
            return pjp.proceed();
        }
        try {
            String id = pjp.getSignature().getDeclaringTypeName() + "-" + pjp.getSignature().getName();
            Method method = methodProcess(pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
            Type type = method.getAnnotatedReturnType().getType();
            Object object = ObjectMock.getObjectAndCache(id,type, 4);
            return object;
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] MockAspect.around exception：");
        }
        return null;
    }

    private Method methodProcess(String className, String methodName) {
        Method method = null;
        try {
            Class clazz = Class.forName(className);
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                // 目前不支持方法重载，后续优化
                if (m.getName().equalsIgnoreCase(methodName)) {
                    method = m;
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            MockContext.LOG.error("[O-MOCK] MockAspect.methodProcess exception：");
            throw new RuntimeException(e);
        }
        return method;
    }
}
