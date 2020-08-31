package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.constraint.IConst;

import java.util.Properties;

public class PropertiesUtil {

    public static Boolean MOCK_ON = false;

    public static Boolean OPEN_RECORD = false;

    public static String PROJECT_NAME = "";

    public static String PATH = "/export/data/mock";


    static {
        try {
            Properties properties = new Properties();
            properties.load(PropertiesUtil.class.getResourceAsStream("/mock.properties"));

            String mockOnStr = properties.getProperty(IConst.MOCK_PROPERTIES_MOCK_ON,"false");
            if ("true".equals(mockOnStr)) {
                MOCK_ON = true;
            }
            String openRecordStr = properties.getProperty(IConst.MOCK_PROPERTIES_OPEN_RECORD,"false");
            if ("true".equals(openRecordStr)) {
                OPEN_RECORD = true;
            }
            PROJECT_NAME = properties.getProperty(IConst.MOCK_PROPERTIES_APP_NAME,"we-end");
            PATH = properties.getProperty(IConst.MOCK_PROPERTIES_CONFIG_PATH,"/export/data/mock");

        } catch (Exception e) {}
    }

}
