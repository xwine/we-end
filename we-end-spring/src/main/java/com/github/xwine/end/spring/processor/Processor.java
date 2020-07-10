package com.github.xwine.end.spring.processor;

import com.alibaba.fastjson.JSON;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public interface Processor {
    JSON process(WebApplicationContext wac, Map<String, String[]> params);
}