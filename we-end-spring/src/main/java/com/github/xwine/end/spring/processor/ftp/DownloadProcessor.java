package com.github.xwine.end.spring.processor.ftp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.spi.MockFileService;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class DownloadProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        try {
            String user = params.get("user") == null ? "" : params.get("user")[0];
            String fileName = params.get("fileName") == null ? "" : params.get("fileName")[0];
            MockFileService mockFileService = wac.getBean(MockFileService.class);
            if (mockFileService != null) {
                mockFileService.downloadFile(user, fileName);
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] DownloadProcessor.process exception");
        }
        return result;
    }
}
