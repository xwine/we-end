package com.github.xwine.end.spring.processor.loc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.oss.JDFileService;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.mock.MockContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class UploadProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        try {
            boolean success = false;
            //上传子文件
            String fileName = params.get("fileName") == null ? "" : params.get("fileName")[0];
            success = this.uploadFile(fileName);
            if (success) {
                result.put("message","上传成功");
            } else {
                result.put("message","上传失败");
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] UploadProcessor.processor exception");
        }
        return result;
    }

    private boolean uploadFile(String fileName) {
        return JDFileService.uploadFile(fileName);
    }
}
