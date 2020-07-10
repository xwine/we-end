package com.github.xwine.end.spring.processor.loc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.util.FileUtil;
import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

public class GetLocDataProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        JSONObject result = new JSONObject();
        try {
            String fileName = params.get("fileName") == null ? "" : params.get("fileName")[0];
            String filePath = MockContext.getConfig().getPath() + "/" + MockContext.getConfig().getIdCachePath() + "/" + fileName;
            String json = FileUtil.getDataFromFile(filePath);
            if (StringUtils.isNotEmpty(json) && (!"null".equals(json))) {
                return this.parseJson(json);
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] GetLocDataProcessor.processor exception", e);
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
