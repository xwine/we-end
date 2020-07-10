package com.github.xwine.end.spring.processor.beans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.util.InvokeUtil;
import com.github.xwine.end.mock.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class GetParamsProcessor implements Processor {

    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        String beanName = params.get("beanName") == null ? "" : params.get("beanName")[0];
        String methodName = params.get("methodName") == null ? "" : params.get("methodName")[0];

        if (StringUtils.isEmpty(beanName) || StringUtils.isEmpty(methodName)|| params.isEmpty()) {
            result.put("code", 400);
            result.put("msg", "所有参数必填");
            return result;
        }
        return InvokeUtil.getParamsJson(wac,beanName,methodName);
    }

}
