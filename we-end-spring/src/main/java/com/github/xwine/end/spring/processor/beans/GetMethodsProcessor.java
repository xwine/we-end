package com.github.xwine.end.spring.processor.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.util.AopTargetUtils;
import com.github.xwine.end.spring.util.InvokeUtil;
import com.github.xwine.end.mock.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 获取一个bean的所有公用方法的处理器
 * 参数名: beanName 或 beanType 二选一  两个都有的话  beanName优先
 */
public class GetMethodsProcessor implements Processor {

    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        String beanName = params.get("beanName") == null ? "" : params.get("beanName")[0];
        String beanType = params.get("beanType") == null ? "" : params.get("beanType")[0];
        if (StringUtils.isEmpty(beanName) && StringUtils.isEmpty(beanType)) {
            result.put("code", "400");
            result.put("msg", "必须制定bean名称或类型");
            return result;
        }

        Class clazz = null;
        if (!StringUtils.isEmpty(beanName)) {
            Object bean = wac.getBean(beanName);
            if (bean == null) {
                result.put("code", "400");
                result.put("msg", "Bean Not Found:" + beanName);
                return result;
            }
            try {
                Object targetBean = AopTargetUtils.getTarget(bean);
                clazz = AopTargetUtils.getTargetClass(targetBean.getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(!StringUtils.isEmpty(beanType)) {
            try {
                clazz = Class.forName(beanType);
            } catch (ClassNotFoundException e) {
                result.put("code", "400");
                result.put("msg", "Class Not Found:" + beanType);
                return result;
            }
        }
        if (clazz == null) {
            result.put("code", "400");
            result.put("msg", "Class Not Found:" + beanType);
            return result;
        }
        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null) {
            for (Method m : methods) {
                result.put(InvokeUtil.methodSign(m), InvokeUtil.descMethod(m));
            }
        }
        return result;
    }


}
