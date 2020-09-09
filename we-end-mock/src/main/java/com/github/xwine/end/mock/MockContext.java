package com.github.xwine.end.mock;

import com.github.xwine.end.mock.constraint.IConst;
import com.github.xwine.end.mock.gson.JsonElement;
import com.github.xwine.end.mock.util.FileUtils;
import com.github.xwine.end.mock.util.JsonUtils;
import com.github.xwine.end.mock.util.PropertiesUtil;
import com.github.xwine.end.mock.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MockContext {
    public final static Logger LOG = LoggerFactory.getLogger(MockContext.class);

    /**
     * 是否本地开发环境
     */
    private static boolean ENV_DEV = false;

    private static MockConfig configCache = null;

    static {
       initEnv();
    }

    private static void initEnv() {
        String openWeEnd = System.getProperty(IConst.JVM_ENV_PROPERTY);
        if (StringUtils.isNotEmpty(openWeEnd)) {
            if ("true".equals(openWeEnd)) {
                ENV_DEV = true;
                return;
            }
            if ("false".equals(openWeEnd)) {
                ENV_DEV = false;
                return;
            }
        }
        // 检测到环境是mac os 或者 windows 则默认开启we end
        List<String> supportOs = new ArrayList<>();
        supportOs.add("Mac OS X");
        supportOs.add("Windows 7");
        supportOs.add("Windows 8");
        supportOs.add("Windows 10");
        String osName = System.getProperty("os.name");
        if (StringUtils.isNotBlank(osName) && supportOs.contains(osName)) {
            ENV_DEV = true;
        }
    }

    /**
     * get absolute path by classloader
     * @return
     */
    private static String getDirPath() {
        if (MockContext.class.getClassLoader().getResource("") != null) {
            String projectPath = MockContext.class.getClassLoader().getResource("").getPath();
            if (StringUtils.isEmpty(projectPath)) {
                return null;
            }
            int tagIndex = projectPath.indexOf("target");
            if (tagIndex <= 0) {
                return null;
            }
            String subPath = projectPath.substring(0, tagIndex - 1);
            if (StringUtils.isEmpty(subPath)) {
                return null;
            }
            String[] paths = subPath.split("/");
            if (paths == null || paths.length < 1) {
                return null;
            }
            // **/A/A-a  => A
            if (paths.length >= 2 && paths[paths.length - 1].contains(paths[paths.length - 2])) {
                int lastIndex = projectPath.indexOf(paths[paths.length - 1]);
                if (lastIndex > 0) {
                    subPath = subPath.substring(0, lastIndex - 1);
                }
            }
            String mockDir = subPath + "/" + IConst.MOCK_FILE_NAME;
            File file = new File(mockDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return mockDir;
        }
        return  "/" + IConst.MOCK_FILE_NAME;
    }

    /**
     * 获取项目名
     * @param dir
     * @return
     */
    private static String getProjectName(String dir) {
        try {
            String[] paths =dir.split("/");
            if (paths != null && paths.length > 1) {
                return paths[paths.length-2];
            }
        } catch (Exception e) {}
        return MockConfig.initAppName();
    }

    /**
     * .mock/config.json
     * @return
     */
    public static MockConfig getConfig() {
        try {
            //不为空则走缓存，意味着修改配置都需要重启应用
            if (configCache != null) {
                return configCache;
            }
            //如果开发环境，走config.json配置
            if (ENV_DEV) {
                return fetchDevConfig();
            } else {
                //特殊环境 比如测试及预发布 走单独配置
                return specialEnvConfig();
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK][read config.json error ,all config use default]");
        }
        return new MockConfig();
    }

    private static MockConfig fetchDevConfig() {
        String mockDir = getDirPath();
        File dic = new File(mockDir);
        if (dic.exists()) {
            String path = mockDir + "/" + "config.json";
            File file = new File(path);
            if (!file.exists()) {
                //第一次创建配置文件
                LOG.info("[O-MOCK] First use ?,Auto create config.json file...");
                //文件不存在，初始化文件内容
                MockConfig mockConfig = new MockConfig();
                mockConfig.setPath(mockDir);
                mockConfig.setAppName(getProjectName(mockDir));
                mockConfig.setNowUser(MockConfig.initNowUser());
                mockConfig.setMockOn(true);
                mockConfig.setWeEndOn(true);
                JsonElement jsonElement = JsonUtils.toJsonTree(mockConfig);
                FileUtils.saveJsonToFile(jsonElement, path);
                //缓存配置
                configCache = mockConfig;
                return configCache;
            } else {
                //之前创建过配置文件
                JsonElement jsonElement = FileUtils.getObjectFromFile(path);
                Object config = JsonUtils.fromJson(jsonElement, MockConfig.class);
                if (config instanceof MockConfig) {
                    //缓存配置
                    configCache =  (MockConfig) config;
                    return configCache;
                }
            }
        }
        return new MockConfig();
    }

    private static MockConfig specialEnvConfig() {
        try {
            URL url = MockContext.class.getResource("/config.json");
            if (url == null) {
                MockContext.LOG.info("[O-MOCK][special config.json not find ,all config use default]");
                return new MockConfig();
            }
            //之前创建过配置文件
            JsonElement jsonElement = FileUtils.getObjectFromFile(url.getFile());
            Object config = JsonUtils.fromJson(jsonElement, MockConfig.class);
            if (config instanceof MockConfig) {
                //缓存配置
                configCache = (MockConfig) config;
                return configCache;
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK][read special config.json error ,all config use default]");
        }
        return new MockConfig();
    }


    public static void main(String[] args) {
        System.out.println(specialEnvConfig().toString());
    }



}
