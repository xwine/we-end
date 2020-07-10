package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.constraint.IConst;

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
    public static String getBasicMockFilePath() {
         return MockContext.getConfig().getPath()+"/"+ IConst.MOCK_BASIC_FILE_NAME;
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


    /**
     * 获取mock功能是否开启
     * @return
     */
    public static boolean getOpenMock() {
        String property = System.getProperty(IConst.OPEN_MOCK);
        if (StringUtils.isNotEmpty(property) && property.equals("true")) {
            return true;
        }
        return IConst.OPEN_MOCK_DEFAULT;
    }

    /**
     * 获取基础数据缓存功能是否开启
     * @return
     */
    public static boolean getCloseCache() {
        String property = System.getProperty(IConst.CLOSE_CACHE);
        if (StringUtils.isNotEmpty(property) && property.equals("true")) {
            return true;
        }
        return IConst.CLOSE_CACHE_DEFAULT;
    }

    public static void main(String[] args) {

    }
}
