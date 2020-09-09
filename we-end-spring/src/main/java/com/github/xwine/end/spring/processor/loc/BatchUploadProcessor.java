package com.github.xwine.end.spring.processor.loc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.spi.MockFileService;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class BatchUploadProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        try {
            boolean success = false;
            MockFileService mockFileService = wac.getBean(MockFileService.class);
            if (mockFileService != null) {
                success = mockFileService.batchUploadFile();
                if (success) {
                    result.put("message", "上传成功");
                } else {
                    result.put("message", "上传失败");
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] UploadProcessor.processor exception");
        }
        return result;
    }
}
