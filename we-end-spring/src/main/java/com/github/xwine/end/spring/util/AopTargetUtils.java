package com.github.xwine.end.spring.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * 获取代理对象的target对象
 */
public class AopTargetUtils {  
  
      
    /** 
     * 获取 目标对象 
     * @param proxy 代理对象 
     * @return  
     * @throws Exception
     */  
    public static Object getTarget(Object proxy) throws Exception {
          
        if(!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象  
        }  
          
        if(AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);  
        } else { //cglib  
            return getCglibProxyTargetObject(proxy);  
        }  
          
          
          
    }

    /**
     * 获取目标对象类
     * @param clazz
     * @return
     */
    public static Class getTargetClass(Class<?> clazz) {
        if(clazz.getCanonicalName().contains("_proxy_")) {
            String targetClassName = clazz.getCanonicalName().substring(0, clazz.getCanonicalName().indexOf("_proxy_"));
            try {
                return Class.forName(targetClassName);
            } catch (ClassNotFoundException e) {
                // 这种简单粗暴的用名字来判断是否为代理类的方式有问题，还是返回原来的类吧
                return clazz;
            }
        } else {
            return clazz;
        }

    }
  
  
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);  
        Object dynamicAdvisedInterceptor = h.get(proxy);
          
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);  
          
        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
          
        return target;  
    }  
  
  
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);  
        AopProxy aopProxy = (AopProxy) h.get(proxy);
          
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);  
          
        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
          
        return target;  
    }


}