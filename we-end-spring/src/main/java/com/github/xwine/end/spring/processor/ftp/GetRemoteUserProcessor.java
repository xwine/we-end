package com.github.xwine.end.spring.processor.ftp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.spi.MockFileService;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class GetRemoteUserProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONArray array = new JSONArray();
        try {
            MockFileService mockFileService = wac.getBean(MockFileService.class);
            if (mockFileService != null) {
                for (String s : mockFileService.fetchDicList()) {
                    array.add(s);
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] GetRemoteUserProcessor.process exception");
        }
        return array;
    }
}
