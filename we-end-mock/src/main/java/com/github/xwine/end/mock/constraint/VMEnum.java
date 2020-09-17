package com.github.xwine.end.mock.constraint;

/**
 * JVM 参数枚举值
 * @author chenjunjun
 * @date 2020-09-16 14:04
 */

public enum VMEnum {
    /**
     * app name
     */
    APP_NAME("we.end.app.name","we-end"),

    /**
     * config.json 文件路径
     */
    CONFIG_PATH("we.end.config.path","/export/data/mock/config.json"),

    /**
     * JRE 默认带该参数，不用显示指定
     */
    OS_NAME("os.name","null"),

    /**
     * user
     * JRE 默认带该参数，不用显示指定
     */
    USER_NAME("user.name","null");

    public String getKey() {
        return key;
    }

    /**
     * JVM -D
     * @param key
     */
    VMEnum(String key,String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * vm 值
     */
    private String key;

    /**
     * vm 默认值
     */
    private String value;

    public String getValue() {
        return value;
    }
}
