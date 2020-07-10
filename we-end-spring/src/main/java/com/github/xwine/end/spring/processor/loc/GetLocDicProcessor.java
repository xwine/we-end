package com.github.xwine.end.spring.processor.loc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xwine.end.spring.processor.Processor;
import com.github.xwine.end.spring.util.FileUtil;
import com.github.xwine.end.mock.MockContext;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.List;
import java.util.Map;

public class GetLocDicProcessor implements Processor {
    @Override
    public JSON process(WebApplicationContext wac, Map<String, String[]> params) {
        try {
            JSONArray array = new JSONArray();
            String filePath = MockContext.getConfig().getPath()+"/"+MockContext.getConfig().getIdCachePath();
            List<File> files = FileUtil.getAllFiles(filePath);
            for (File file : files) {
                if (file.isFile()) {
                    array.add(file.getName());
                }
            }
            return array;
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] GetLocDicProcessor.processor exception");
        }
        return null;
    }
}
