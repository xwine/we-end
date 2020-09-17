package com.github.xwine.end.mock.constraint;

/**
 *
 */
public enum FileEnum {
    /**
     * mock数据存放文件夹名称
     */
    MOCK_DIC(".mock");
    FileEnum(String value) {
        this.value = value;
    }

    /**
     * os 值
     */
    private String value;

    public String getValue() {
        return value;
    }
}
