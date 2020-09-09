package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.constraint.IConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {

    public static Boolean WE_END_ON = false;

    public static Boolean MOCK_ON = false;

    public static Boolean OPEN_RECORD = false;

    public static String PROJECT_NAME = "";

    public static String PATH = "/export/data/mock";
    public static String NOW_USER = "no-name";
    public static String METHOD_CACHE = "data";

    public static List<String> mockClasses = new ArrayList<>();


    static {
        try {
            Properties properties = new Properties();
            properties.load(PropertiesUtil.class.getResourceAsStream("/mock.properties"));

            String weEndOnStr = properties.getProperty(IConst.MOCK_PROPERTIES_WE_END_ON,"false");
            if ("true".equals(weEndOnStr)) {
                WE_END_ON = true;
            }
            String mockOnStr = properties.getProperty(IConst.MOCK_PROPERTIES_MOCK_ON,"false");
            if ("true".equals(mockOnStr)) {
                MOCK_ON = true;
            }
            String openRecordStr = properties.getProperty(IConst.MOCK_PROPERTIES_OPEN_RECORD,"false");
            if ("true".equals(openRecordStr)) {
                OPEN_RECORD = true;
            }
            String mockClassStr = properties.getProperty(IConst.MOCK_PROPERTIES_CONFIG_MOCK_CLASS);
            if (StringUtils.isNotEmpty(mockClassStr)) {
                String[] mockClassArray = mockClassStr.split(",");
                if (mockClassArray != null && mockClassArray.length>0) {
                    for (String classStr : mockClassArray) {
                        mockClasses.add(classStr);
                    }
                }
            }
            PROJECT_NAME = properties.getProperty(IConst.MOCK_PROPERTIES_APP_NAME,"we-end");
            PATH = properties.getProperty(IConst.MOCK_PROPERTIES_CONFIG_PATH,"/export/data/mock");
            NOW_USER = properties.getProperty(IConst.MOCK_PROPERTIES_NOW_USER,"no-name");
            METHOD_CACHE = properties.getProperty(IConst.MOCK_PROPERTIES_METHOD_CACHE,"data");

        } catch (Exception e) {}
    }

}
