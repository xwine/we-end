package com.github.xwine.end.mock;

import com.github.xwine.end.mock.constraint.FileEnum;
import com.github.xwine.end.mock.constraint.OSEnum;
import com.github.xwine.end.mock.constraint.VMEnum;
import com.github.xwine.end.mock.gson.JsonElement;
import com.github.xwine.end.mock.util.FileUtils;
import com.github.xwine.end.mock.util.JsonUtils;
import com.github.xwine.end.mock.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MockContext {
    public final static Logger LOG = LoggerFactory.getLogger(MockContext.class);

    /** 是否本地开发环境 */
    private static boolean ENV_DEV = false;
    /** 配置路径 */
    private static String CONFIG_PATH = null;
    private static MockConfig configCache = null;

    static {
       initEnv();
    }

    private static void initEnv() {
        String configPath = System.getProperty(VMEnum.CONFIG_PATH.getKey());
        if (StringUtils.isNotEmpty(configPath)) {
            CONFIG_PATH = configPath;
        }
        String osName = System.getProperty(VMEnum.OS_NAME.getKey());
        if (StringUtils.isNotBlank(osName) && OSEnum.contains(osName)) {
            ENV_DEV = true;
        }
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
            //vm 是否配置path
            if (StringUtils.isNotEmpty(CONFIG_PATH)) {
                configCache = fetchConfig(CONFIG_PATH);
                return configCache;
            }
            // 开发环境自动创建
            if (ENV_DEV) {
                String configPath = getDirPath()+"/config.json";
                configCache = fetchConfig(configPath);
                return configCache;
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK][read config.json error ,all config use default]");
        }
        return new MockConfig();
    }

    /**
     * 根据绝对路径获取配置
     * @param configPath
     * @return
     */
    private static MockConfig fetchConfig(String configPath) {
        if (StringUtils.isBlank(configPath)) {
            return new MockConfig();
        }
        File file = new File(configPath);
        if (file.exists()) {
            JsonElement jsonElement = FileUtils.getObjectFromFile(configPath);
            Object config = JsonUtils.fromJson(jsonElement, MockConfig.class);
            if (config instanceof MockConfig) {
                LOG.info("[O-MOCK]  config.json file is good ");
                return (MockConfig) config;
            }
            LOG.warn("[O-MOCK]  config.json file is not good ");
            return new MockConfig();
        }
        //不存在则创建配置文件
        LOG.warn("[O-MOCK] [config.json] file is not exists");
        //非开发环境，不能自动创建配置文件
        if (ENV_DEV == false) {
            return new MockConfig();
        }
        //文件不存在，初始化文件内容
        MockConfig mockConfig = new MockConfig();
        mockConfig.setNowUser(MockConfig.initNowUser());
        mockConfig.setMockOn(true);
        mockConfig.setWeEndOn(true);
        mockConfig.setPath(getDirPath());
        mockConfig.setAppName(getProjectName());
        JsonElement jsonElement = JsonUtils.toJsonTree(mockConfig);
        FileUtils.saveJsonToFile(jsonElement, configPath);
        return mockConfig;
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
            String mockDir = subPath + "/" + FileEnum.MOCK_DIC.getValue();
            File file = new File(mockDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return mockDir;
        }
        return  "/" + FileEnum.MOCK_DIC.getValue();
    }

    /**
     * 获取项目名
     * @return
     */
    private static String getProjectName() {
        try {
            String[] paths =getDirPath().split("/");
            if (paths != null && paths.length > 1) {
                return paths[paths.length-2];
            }
        } catch (Exception e) {}
        return MockConfig.initAppName();
    }

    public static void main(String[] args) {
        System.out.println(getConfig());

    }



}
