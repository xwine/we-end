package com.github.xwine.end.spring.processor.ftp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.spi.MockFileService;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

public class GetRemoteDicProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONArray array = new JSONArray();
        try {
            String user = params.get("user") == null ? "" : params.get("user")[0];
            MockFileService mockFileService = wac.getBean(MockFileService.class);
            if (mockFileService != null) {
                List<String> fileTree = mockFileService.fetchFiles(user);
                for (String s : fileTree) {
                    array.add(s);
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] GetRemoteDicProcessor.process exception");
        }
        return array;
    }

}
