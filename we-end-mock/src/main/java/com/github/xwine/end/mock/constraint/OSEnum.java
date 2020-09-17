package com.github.xwine.end.mock.constraint;

/**
 * System.getProperty("os.name");
 * 支持自动开启mock的操作系统
 */
public enum OSEnum {

    /**
     * mac
     */
    MAC("Mac OS X"),
    /**
     * win7
     */
    WIN7("Windows 7"),
    /**
     * win8
     */
    WIN8("Windows 8"),
    /**
     * win10
     */
    WIN10("Windows 10");

    /**
     * OS
     * @param value
     */
    OSEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * os 值
     */
    private String value;

    /**
     * 是否包含操作系统
     * @param value
     * @return
     */
    public static boolean contains(String value) {
        for (OSEnum osEnum : OSEnum.values()) {
            if (osEnum.value.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
