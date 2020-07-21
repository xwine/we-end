package com.github.xwine.end.spring.processor.ftp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.util.StringUtils;
import com.github.xwine.end.spring.spi.MockFileService;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class GetRemoteDataProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        try {
            String user = params.get("user") == null ? "" : params.get("user")[0];
            String fileName = params.get("fileName") == null ? "" : params.get("fileName")[0];
            MockFileService mockFileService = wac.getBean(MockFileService.class);
            if (mockFileService != null) {
                String json = mockFileService.fetchFileContent(user, fileName);
                if (StringUtils.isNotEmpty(json) && (!"null".equals(json))) {
                    return this.parseJson(json);
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] GetRemoteDataProcessor.process exception");
        }
        return result;
    }


    private JSON parseJson(String json) {
        Object obj = JSON.parse(json);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        return (JSONObject) JSON.toJSON(obj);
    }
}
