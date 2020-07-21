package com.github.xwine.end.mock;

import com.github.xwine.end.mock.constraint.IConst;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockConfig {

    /**
     *  open or close mock
     */
    private Boolean mockOn = false;
    private String appName;
    private String nowUser;
    private String path;
    private boolean localCacheSwitch = false;
    private boolean remoteCacheSwitch = true;
    private String idCachePath = "data";
    private String template = "type";
    private String templateString = "abc";
    private Integer templateInteger = 123;
    private Long templateLong = 12345L;
    private Double templateDouble = 1111111D;
    private Float templateFloat = 12345F;
    private BigDecimal templateBigDecimal = new BigDecimal(2020);
    private Boolean templateBoolean = true;
    private Character templateCharacter = 'o';
    private Byte templateByte = new Byte("1");
    private Date templateDate = new Date();
    private String consolePrefix = "/console";
    private List<String> mockClasses = new ArrayList<>();

    public MockConfig() {
    }

    public Boolean getMockOn() {
        return mockOn;
    }

    public void setMockOn(Boolean mockOn) {
        this.mockOn = mockOn;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNowUser() {
        return nowUser;
    }

    public void setNowUser(String nowUser) {
        this.nowUser = nowUser;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isLocalCacheSwitch() {
        return localCacheSwitch;
    }

    public void setLocalCacheSwitch(boolean localCacheSwitch) {
        this.localCacheSwitch = localCacheSwitch;
    }

    public boolean isRemoteCacheSwitch() {
        return remoteCacheSwitch;
    }

    public void setRemoteCacheSwitch(boolean remoteCacheSwitch) {
        this.remoteCacheSwitch = remoteCacheSwitch;
    }

    public String getIdCachePath() {
        return idCachePath;
    }

    public void setIdCachePath(String idCachePath) {
        this.idCachePath = idCachePath;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateString() {
        return templateString;
    }

    public void setTemplateString(String templateString) {
        this.templateString = templateString;
    }

    public Integer getTemplateInteger() {
        return templateInteger;
    }

    public void setTemplateInteger(Integer templateInteger) {
        this.templateInteger = templateInteger;
    }

    public Long getTemplateLong() {
        return templateLong;
    }

    public void setTemplateLong(Long templateLong) {
        this.templateLong = templateLong;
    }

    public Double getTemplateDouble() {
        return templateDouble;
    }

    public void setTemplateDouble(Double templateDouble) {
        this.templateDouble = templateDouble;
    }

    public Float getTemplateFloat() {
        return templateFloat;
    }

    public void setTemplateFloat(Float templateFloat) {
        this.templateFloat = templateFloat;
    }

    public BigDecimal getTemplateBigDecimal() {
        return templateBigDecimal;
    }

    public void setTemplateBigDecimal(BigDecimal templateBigDecimal) {
        this.templateBigDecimal = templateBigDecimal;
    }

    public Boolean getTemplateBoolean() {
        return templateBoolean;
    }

    public void setTemplateBoolean(Boolean templateBoolean) {
        this.templateBoolean = templateBoolean;
    }

    public Character getTemplateCharacter() {
        return templateCharacter;
    }

    public void setTemplateCharacter(Character templateCharacter) {
        this.templateCharacter = templateCharacter;
    }

    public Byte getTemplateByte() {
        return templateByte;
    }

    public void setTemplateByte(Byte templateByte) {
        this.templateByte = templateByte;
    }

    public Date getTemplateDate() {
        return templateDate;
    }

    public void setTemplateDate(Date templateDate) {
        this.templateDate = templateDate;
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    public void setConsolePrefix(String consolePrefix) {
        this.consolePrefix = consolePrefix;
    }

    public List<String> getMockClasses() {
        return mockClasses;
    }

    public void setMockClasses(List<String> mockClasses) {
        this.mockClasses = mockClasses;
    }

    @Override
    public String toString() {
        return "MockConfig{" +
                "mockOn=" + mockOn +
                ", appName='" + appName + '\'' +
                ", nowUser='" + nowUser + '\'' +
                ", path='" + path + '\'' +
                ", localCacheSwitch=" + localCacheSwitch +
                ", remoteCacheSwitch=" + remoteCacheSwitch +
                ", idCachePath='" + idCachePath + '\'' +
                ", template='" + template + '\'' +
                ", templateString='" + templateString + '\'' +
                ", templateInteger=" + templateInteger +
                ", templateLong=" + templateLong +
                ", templateDouble=" + templateDouble +
                ", templateFloat=" + templateFloat +
                ", templateBigDecimal=" + templateBigDecimal +
                ", templateBoolean=" + templateBoolean +
                ", templateCharacter=" + templateCharacter +
                ", templateByte=" + templateByte +
                ", templateDate=" + templateDate +
                ", consolePrefix='" + consolePrefix + '\'' +
                ", mockClasses=" + mockClasses +
                '}';
    }


    public static String initNowUser() {
        return System.getProperty("user.name","no-name");
    }

    public static boolean initMockOn() {
        String mockOn = System.getProperty(IConst.PROD_DATA_CONFIG_MOCK_ON,"false");
        if ("true".equals(mockOn)) {
            return true;
        }
        return false;
    }

    public static String initAppName() {
        return System.getProperty(IConst.PROD_DATA_CONFIG_APP_NAME,"we-end");
    }

    public static String initPath() {
        return System.getProperty(IConst.PROD_DATA_CONFIG_PATH,"/export/data/mock");
    }
}
