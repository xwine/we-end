package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.MockContext;

import java.io.File;

/**
 * @author xwine
 * @date 2020-03-05 21:41
 */
public class EnvUtils {

    public static String getMockCacheIdFile(String cacheId) {
       String mockDir = MockContext.getConfig().getPath()+"/"+MockContext.getConfig().getIdCachePath();
        File file = new File(mockDir);
        if (!file.exists()) {
            file.mkdirs();
        }
       return mockDir+"/"+cacheId+".json";
    }

    /**
     * 获取mock数据文件路径
     * @return
     */
    public static String getBasicMockFilePath(String name) {
        String mockDir = MockContext.getConfig().getPath()+"/"+MockContext.getConfig().getTemplate();
        File file = new File(mockDir);
        if (!file.exists()) {
            file.mkdirs();
        }
         return mockDir+"/"+name+".json";
    }

    public static void main(String[] args) {

    }
}
