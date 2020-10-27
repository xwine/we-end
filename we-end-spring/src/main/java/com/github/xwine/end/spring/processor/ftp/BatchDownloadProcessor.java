package com.github.xwine.end.spring.processor.ftp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.spi.MockFileService;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class BatchDownloadProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        try {
            String user = params.get("user") == null ? "" : params.get("user")[0];
            MockFileService mockFileService = wac.getBean(MockFileService.class);
            boolean success = false;
            if (mockFileService != null) {
                success = mockFileService.batchDownloadFile(user);
                if (success) {
                    result.put("message", "下载成功");
                } else {
                    result.put("message", "下载失败");
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] DownloadProcessor.process exception");
        }
        return result;
    }
}
