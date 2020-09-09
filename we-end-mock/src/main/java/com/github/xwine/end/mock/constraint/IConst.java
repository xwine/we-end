package com.github.xwine.end.mock.constraint;

/**
 * @author xwine
 * @date 2020-03-05 21:32
 */
public interface IConst {
    String MOCK_FILE_NAME = ".mock";
    String MOCK_BASIC_FILE_NAME = "basic.json";
    String OPEN_MOCK = "open_object_mock";
    String CLOSE_CACHE = "close_object_mock_cache";
    boolean CLOSE_CACHE_DEFAULT = false;
    boolean OPEN_MOCK_DEFAULT = false;
    String PROD_DATA_CONFIG_APP_NAME="mock.config.projectName";
    String PROD_DATA_CONFIG_MOCK_ON="mock.config.mockOn";
    String PROD_DATA_CONFIG_PATH="mock.config.path";
    String MOCK_PROPERTIES_WE_END_ON = "we.end.on";
    String MOCK_PROPERTIES_MOCK_ON = "we.end.mock.on";
    String MOCK_PROPERTIES_OPEN_RECORD = "we.end.open.record";
    String MOCK_PROPERTIES_APP_NAME = "we.end.project.name";
    String MOCK_PROPERTIES_NOW_USER = "we.end.now.user";
    String MOCK_PROPERTIES_METHOD_CACHE = "we.end.method.cache";
    String MOCK_PROPERTIES_CONFIG_PATH = "we.end.config.path";
    String MOCK_PROPERTIES_CONFIG_MOCK_CLASS = "we.end.config.mockClasses";
    String JVM_ENV_PROPERTY = "open.we.end";
}
