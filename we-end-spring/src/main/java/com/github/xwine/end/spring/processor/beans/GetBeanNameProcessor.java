package com.github.xwine.end.spring.processor.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.processor.Processor;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class GetBeanNameProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        String[] beanNames;
        try {
            beanNames = wac.getBeanDefinitionNames();
        } catch (Exception e) {
            beanNames = new String[0];
        }
        result.put("beanNames", beanNames);
        return result;
    }
}
