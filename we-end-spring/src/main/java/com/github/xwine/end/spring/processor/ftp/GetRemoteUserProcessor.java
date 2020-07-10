package com.github.xwine.end.spring.processor.ftp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xwine.end.spring.oss.JDFileService;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.mock.MockContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class GetRemoteUserProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONArray array = new JSONArray();
        try {
            for (String s : JDFileService.fetchDicList()) {
                array.add(s);
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] GetRemoteUserProcessor.process exception");
        }
        return array;
    }
}
