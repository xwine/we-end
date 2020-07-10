package com.github.xwine.end.spring.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * 默认的 空的处理器
 */
public class EmptyProcessor implements Processor {

    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        return new JSONObject();
    }
}